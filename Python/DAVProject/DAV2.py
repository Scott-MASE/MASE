import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from tabulate import tabulate


class DAV_Project_Driver:
    def __init__(self):
        self.data = {}
        self.F_GPUData = None
        self.F_CPUData = None

        # Load data
        self.data["dfGPU"] = pd.read_csv("data/All_GPUs.csv")
        self.data["CPU_GPU"] = pd.read_csv("data/chip_dataset.csv")
        self.data["CPUBench"] = pd.read_csv("data/CPU_benchmark_v4.csv")

        print("\nOverview of raw data:")
        self.overview()

        print("\nCleaning data...")
        self.clean_data()

        print("\nData Analysis:")
        self.analyse_data()

    def overview(self):
        for name, df in self.data.items():
            print(f"\nTable: {name}")
            self.print_table(df.head())

    def clean_data(self):
        self.clean_cpu_data()
        self.clean_gpu_data()

    def clean_cpu_data(self):
        ex_CPU = self.data["CPU_GPU"][self.data["CPU_GPU"]["Type"] == "CPU"].copy()
        ex_CPU.rename(columns={"Product": "CPUname"}, inplace=True)
        self.data["CPUBench"].rename(columns={"cpuName": "CPUname"}, inplace=True)

        # Process Intel CPUs
        intel_data_1 = self.filter_and_normalize(ex_CPU, "Intel", "CPUname")
        intel_data_2 = self.filter_and_normalize(
            self.data["CPUBench"], "Intel", "CPUname", remove_ghz=True
        )
        merged_intel_df = self.merge_data(intel_data_1, intel_data_2, "CPUname")

        # Process AMD CPUs
        amd_data_1 = self.filter_and_normalize(ex_CPU, "AMD", "CPUname")
        amd_data_2 = self.filter_and_normalize(
            self.data["CPUBench"], "AMD", "CPUname", remove_core=True
        )
        merged_amd_df = self.merge_data(amd_data_1, amd_data_2, "CPUname")

        # Combine and clean final CPU data
        self.F_CPUData = pd.concat([merged_intel_df, merged_amd_df], ignore_index=True)
        self.F_CPUData.drop(
            columns=["Unnamed: 0", "FP16 GFLOPS", "FP32 GFLOPS", "FP64 GFLOPS"],
            inplace=True,
        )
        self.F_CPUData.insert(0, "ID", range(1, len(self.F_CPUData) + 1))
        self.F_CPUData.sort_values(by="Release Date", inplace=True)

        self.save_csv(self.F_CPUData, "temp/F_CPU_Data.csv", "Final CPU df")

    def clean_gpu_data(self):
        gpu_data_1 = self.data["dfGPU"].rename(columns={"Name": "GPUname"})
        gpu_data_2 = self.data["CPU_GPU"][
            self.data["CPU_GPU"]["Type"] == "GPU"
        ].rename(columns={"Product": "GPUname"})

        # Process Nvidia GPUs
        nvidia_data_1 = gpu_data_1[gpu_data_1["Manufacturer"] == "Nvidia"].copy()
        nvidia_data_2 = gpu_data_2[gpu_data_2["Vendor"] == "NVIDIA"].copy()
        nvidia_data_2["GPUname"] = nvidia_data_2["GPUname"].str.replace("NVIDIA ", "")
        merged_nvidia = self.merge_data(nvidia_data_1, nvidia_data_2, "GPUname")

        # Process AMD GPUs
        amd_data_1 = gpu_data_1[gpu_data_1["Manufacturer"] == "AMD"].copy()
        amd_data_2 = gpu_data_2[gpu_data_2["Vendor"] == "AMD"].copy()
        amd_data_2["GPUname"] = amd_data_2["GPUname"].str.replace("AMD ", "")
        merged_amd = self.merge_data(amd_data_1, amd_data_2, "GPUname")

        # Combine and clean final GPU data
        self.F_GPUData = pd.concat([merged_nvidia, merged_amd], ignore_index=True)
        self.F_GPUData.drop(
            columns=[
                "Boost_Clock",
                "DVI_Connection",
                "Dedicated",
                "DisplayPort_Connection",
                "HDMI_Connection",
                "SLI_Crossfire",
                "Unnamed: 0",
                "Type",
                "FP16 GFLOPS",
            ],
            inplace=True,
        )
        self.F_GPUData.insert(0, "ID", range(1, len(self.F_GPUData) + 1))

        self.save_csv(self.F_GPUData, "Temp2/F_GPU_Data.csv", "Final GPU df")

    def analyse_data(self):
        # Plot CPU cost over time
        CPUdf = self.F_CPUData
        CPUdf_subset = CPUdf[CPUdf["cpuValue"] <= 160]
        self.plot_scatter_with_trend(
            CPUdf_subset, "Release Date", "cpuValue", "CPU Cost Over Time"
        )

        # Plot CPU die size and cores over time
        plt.scatter(CPUdf["Release Date"], CPUdf["Die Size (mm^2)"], label="Die Size")
        plt.scatter(CPUdf["Release Date"], CPUdf["cores"], label="Cores")
        plt.ylabel("Metrics")
        plt.title("CPU Die Size and Cores Over Time")
        plt.legend()
        plt.show()

    def filter_and_normalize(self, df, keyword, column, remove_ghz=False, remove_core=False):
        filtered_df = df[df[column].str.contains(keyword, case=False, na=False)].copy()
        if remove_ghz:
            filtered_df[column] = filtered_df[column].str.replace(r" @.*GHz", "", regex=True)
        if remove_core:
            filtered_df[column] = filtered_df[column].str.replace(
                r"\b(quad|triple|dual)[-\s]?core\b", "", regex=True
            ).str.strip()
        return filtered_df

    def merge_data(self, df1, df2, on_column):
        return pd.merge(df1, df2, on=on_column, how="inner")

    def plot_scatter_with_trend(self, df, x_col, y_col, title):
        plt.scatter(df[x_col], df[y_col])
        plt.plot(df[x_col], df[x_col], "-")
        plt.xlabel(x_col)
        plt.ylabel(y_col)
        plt.title(title)
        plt.show()

    def save_csv(self, df, path, description):
        df.to_csv(path, index=False)
        print(f"{description} saved to {path}")

    def print_table(self, df):
        print(tabulate(df, headers="keys", tablefmt="pretty"))


def main():
    DAV_Project_Driver()


if __name__ == "__main__":
    main()
