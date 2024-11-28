import pandas as pd
import numpy as np
from tabulate import tabulate
from difflib import SequenceMatcher
import matplotlib.pyplot as plt


class DAV_Project_Driver:
    def __init__(self):
        self.data = []
        url = "data/All_GPUs.csv"
        self.dfGPU = pd.read_csv(url)
        self.data.append(self.dfGPU)
        self.CPU_GPU = pd.read_csv("data/chip_dataset.csv")
        self.data.append(self.CPU_GPU)
        self.CPUBench = pd.read_csv("data/CPU_benchmark_v4.csv")
        self.data.append(self.CPUBench)
        self.F_GPUData = None
        self.F_CPUData = None

        print("\nOverview of raw data:")
        self.overview()
        print("\nCleaning data...")
        self.cleanData()
        print("\nData Analysis:")
        self.analyseData()

    def overview(self):
        for n in self.data:
            name = self.get_attribute_name(n)
            print(f"\nTable: {name}")
            tab(n.head())

    def cleanData(self):
        # this function merges and normalizes data across multiple CSVs
        # Firstly, the intel cpu data
        ex_CPU = self.CPU_GPU[self.CPU_GPU['Type'] == 'CPU'].copy()
        ex_CPU.rename(columns={'Product': 'CPUname'}, inplace=True)
        ex_CPU.to_csv('temp/ex_CPU.csv', index=False)
        print("\nex_CPU table")
        tab(ex_CPU.head())
        ex_CPU_name = ex_CPU['CPUname'].copy()
        print(f"above table has {ex_CPU.shape[0]} rows")
        self.CPUBench.rename(columns={'cpuName': 'CPUname'}, inplace=True)
        self.CPUBench.to_csv("temp/CPUBench.csv", index=False)
        tab(self.CPUBench.head())
        CPUBench_name = self.CPUBench['CPUname'].copy()
        print(f"above table has {self.CPUBench.shape[0]} rows")
        # ex_CPU has fewer rows, so that will be the base to minimize missing data
        # The CPU naming scheme used in ex_CPU will be used, and so the names used in CPUBench must be normalized to match them
        table_names = pd.concat([ex_CPU_name.reset_index(drop=True),CPUBench_name.reset_index(drop=True)],axis=1)
        tab(table_names.head())
        table_names.to_csv("temp/merged_name_data.csv",index=False)
        # separate intel cpus from AMD
        intel_data_1 = ex_CPU[ex_CPU['CPUname'].str.contains('Intel', case=False, na=False)].copy()
        intel_data_1.to_csv('temp/intel_data_1.csv',index=False)
        print(f"intel data 1 length: {intel_data_1.shape[0]}")
        intel_data_2 = self.CPUBench[self.CPUBench['CPUname'].str.contains('Intel', case=False, na=False)].copy()
        # The next line removes the '@x.xxGHz' from the end of any of the names that have that
        intel_data_2.loc[:, 'CPUname'] = intel_data_2['CPUname'].str.replace(r' @.*GHz', '', regex=True)
        intel_data_2.to_csv('temp/intel_data_2.csv',index=False)
        print(f"intel data 2 length: {intel_data_2.shape[0]}")
        merged_intel_df = pd.merge(intel_data_1, intel_data_2, on='CPUname', how='inner')
        merged_intel_df.to_csv('temp/intel_cpu_merged.csv',index=False)
        print(f"merged intel length: {merged_intel_df.shape[0]}")

        # Next, the AMD cpu data
        amd_data_1 = ex_CPU[ex_CPU['CPUname'].str.contains('AMD', case=False, na=False)].copy()
        amd_data_1.to_csv("temp/amd_data_1.csv",index=False)
        print(f"amd_data_1 length = {amd_data_1.shape[0]}")
        amd_data_2 = self.CPUBench[self.CPUBench['CPUname'].str.contains('AMD', case=False, na=False)].copy()
        amd_data_2['CPUname'] = amd_data_2['CPUname'].str.replace(r'\b(quad|triple|dual)[-\s]?core\b', '', regex=True).str.strip()
        amd_data_2.to_csv("temp/amd_data_2.csv",index=False)
        print(f"amd_data_2 length = {amd_data_2.shape[0]}")
        merged_amd_df = pd.merge(amd_data_1,amd_data_2,on='CPUname', how='inner')
        merged_amd_df.to_csv("temp/amd_cpu_merged.csv", index=False)
        print(f"merged amd length: {merged_amd_df.shape[0]}")

        # Finally, merge amd and intel back together for the final cpu df
        self.F_CPUData = pd.concat([merged_intel_df, merged_amd_df],ignore_index=True)
        #adding an index and removing empty cols
        self.F_CPUData = self.F_CPUData.drop(columns=['Unnamed: 0','FP16 GFLOPS','FP32 GFLOPS','FP64 GFLOPS'])

        # add an index and move it to the front col
        self.F_CPUData['ID'] = list(range(0, self.F_CPUData.shape[0]))
        self.F_CPUData = self.F_CPUData[['ID'] + [col for col in self.F_CPUData.columns if col != 'ID']]
        self.F_CPUData = self.F_CPUData.sort_values(by=['Release Date'])
        self.F_CPUData.to_csv('temp/F_CPU_Data.csv',index=False)
        print(f"Final CPU df length: {self.F_CPUData.shape[0]}")

        # next, the gpu data
        GPU_data_1 = self.dfGPU.copy()
        GPU_data_1.rename(columns={"Name": "GPUname"}, inplace=True)
        GPU_data_1.to_csv("Temp2/GPU_Data_1.csv", index=False)
        GPU_name_1 = GPU_data_1[["GPUname"]].copy()
        GPU_data_2 = self.CPU_GPU[self.CPU_GPU["Type"] == "GPU"].copy()
        GPU_data_2.rename(columns={"Product": "GPUname"}, inplace=True)
        GPU_data_2.to_csv("Temp2/GPU_Data_2.csv", index=False)
        GPU_name_2 = GPU_data_2[["GPUname"]].copy()
        table_names = pd.concat(
            [GPU_name_1.reset_index(drop=True), GPU_name_2.reset_index(drop=True)],
            axis=1,
        )
        table_names.to_csv("Temp2/merged_gpu_names.csv", index=False)
        merged_temp = pd.merge(GPU_name_1, GPU_name_2, on="GPUname", how="inner")
        print(
            f"Merged name length: {merged_temp.shape[0]}"
        )  # 25, not nearly enough data, must clean
        # First, separate amd and nvidia to identify unique differences
        nvidia_data_1 = GPU_data_1[GPU_data_1["Manufacturer"] == "Nvidia"].copy()
        nvidia_data_1.to_csv("Temp2/nvidia_data_1.csv", index=False)
        nvidia_data_2 = GPU_data_2[GPU_data_2["Vendor"] == "NVIDIA"].copy()
        nvidia_data_2["GPUname"] = nvidia_data_2["GPUname"].str.replace(
            "NVIDIA ", "", regex=False
        )
        nvidia_data_1["GPUname"] = nvidia_data_1["GPUname"].str.lower()
        nvidia_data_2["GPUname"] = nvidia_data_2["GPUname"].str.lower()
        concat_nvidia = pd.concat(
            [
                nvidia_data_1["GPUname"].reset_index(drop=True),
                nvidia_data_2["GPUname"].reset_index(drop=True),
            ],
            axis=1,
        )
        concat_nvidia.to_csv("Temp2/concat_nvidia.csv", index=False)
        merge_nvidia = pd.merge(nvidia_data_1, nvidia_data_2, on="GPUname", how="inner")
        merge_nvidia.to_csv("Temp2/merge_nvidia.csv", index=False)
        print(f"nvidia 1 length: {nvidia_data_1.shape[0]}")
        print(f"nvidia 2 length: {nvidia_data_2.shape[0]}")
        print(f"Merge nvidia length: {merge_nvidia.shape[0]}")  # 411, much better

        # next, amd
        amd_data_1 = GPU_data_1[GPU_data_1["Manufacturer"] == "AMD"].copy()
        amd_data_2 = GPU_data_2[GPU_data_2["Vendor"] == "AMD"].copy()
        amd_data_1.to_csv("Temp2/amd_data_1.csv", index=False)
        amd_data_2.to_csv("Temp2/amd_data_2.csv", index=False)
        amd_data_1["GPUname"] = amd_data_1["GPUname"].str.lower()
        amd_data_2["GPUname"] = amd_data_2["GPUname"].str.lower()
        amd_data_2["GPUname"] = amd_data_2["GPUname"].str.replace(
            "amd ", "", regex=False
        )
        amd_data_1.to_csv("Temp2/amd_data_1.csv", index=False)
        amd_data_2.to_csv("Temp2/amd_data_2.csv", index=False)
        concat_amd = pd.concat(
            [
                amd_data_1["GPUname"].reset_index(drop=True),
                amd_data_2["GPUname"].reset_index(drop=True),
            ],
            axis=1,
        )

        concat_amd.to_csv("Temp2/concat_amd.csv", index=False)
        merge_amd = pd.merge(amd_data_1, amd_data_2, on="GPUname", how="inner")
        merge_amd.to_csv("Temp2/merge_amd.csv", index=False)
        print(f"amd 1 length: {amd_data_1.shape[0]}")
        print(f"amd 2 length: {amd_data_2.shape[0]}")
        print(f"Merge amd length: {merge_amd.shape[0]}")

        # finally, merge amd and nvidia back together for the final gpu df
        self.F_GPUData = pd.concat([merge_nvidia, merge_amd], ignore_index=True)
        print(f"Final GPU df length: {self.F_GPUData.shape[0]}")
        print(self.F_GPUData.columns)
        self.F_GPUData = self.F_GPUData.drop(
            columns=[
                "Boost_Clock", "DVI_Connection", "Dedicated", "DisplayPort_Connection", 
                "HDMI_Connection", "SLI_Crossfire", "Unnamed: 0", "Type", "FP16 GFLOPS"
                ]
        )
        self.F_GPUData.insert(loc=0, column="index", value=range(1, self.F_GPUData.shape[0] + 1))
        self.F_GPUData.to_csv("Temp2/F_GPU_Data.csv", index=False)

    def analyseData(self):
        CPUdf = self.F_CPUData
        GPUdf = self.F_GPUData


        print(CPUdf.columns)

        CPUdfp1 = CPUdf[CPUdf['cpuValue'] <= 160]
        plt.scatter(CPUdfp1['Release Date'],CPUdfp1['cpuValue'])
        
        z = np.polyfit(CPUdfp1['Release Date'],CPUdfp1['cpuValue'], 1)
        p = np.poly1d(z)
        plt.plot(CPUdfp1['Release Date'],p(CPUdfp1['Release Date']),"-")

        plt.xlabel('Release Date')
        plt.ylabel('Cost')
        plt.title('Cpu cost over time')
        plt.gca().xaxis.set_major_locator(plt.MaxNLocator(10))
        plt.gcf().autofmt_xdate()
        plt.show()

        plt.scatter(CPUdf['Release Date'],CPUdf['Die Size (mm^2)'], marker='o', color='blue')
        plt.scatter(CPUdf['Release Date'],CPUdf['cores'], marker='x', color='red')
        # plt.plot(CPUdf['Release Date'],(CPUdf['Release Date']))          
        plt.ylabel('Die Size (mm^2)')
        plt.title('CPU Die Size and Cores Over Time')
        plt.legend()
        plt.show()
        
        


        



    def get_attribute_name(self, attribute):
        for name, value in self.__dict__.items():
            if value is attribute:
                return name
        return None


def main():

    dav = DAV_Project_Driver()


def tab(str):
    print(tabulate(str, tablefmt="pretty", headers="keys"))


def contains(x, y):
    return x.str.contains(y)


if __name__ == "__main__":
    main()
