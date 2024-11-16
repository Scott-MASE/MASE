import pandas as pd
from tabulate import tabulate


class DAV_Project_Driver:
    def __init__(self):
        self.data = []
        

        # url = 'data/All_GPUs.csv'
        # self.dfGPU = pd.read_csv(url)
        # self.data.append(self.dfGPU)
        self.CPU_GPU = pd.read_csv('data/chip_dataset.csv')
        self.data.append(self.CPU_GPU)
        self.CPUBench = pd.read_csv('data/CPU_benchmark_v4.csv')
        self.data.append(self.CPUBench)
        self.F_GPUData = None
        self.F_CPUData = None

        print("\nOverview of raw data:")
        self.overview()
        print("\nCleaning data...")
        self.cleanData()

    def overview(self):
        for n in self.data:
            name = self.get_attribute_name(n)
            print(f"\nTable: {name}")
            print(n.head())
        

    def cleanData(self):
        # this function merges and normalizes data across multiple CSVs
        # Firstly, the cpu data
        ex_CPU = self.CPU_GPU[self.CPU_GPU['Type'] == 'CPU']
        ex_CPU.rename(columns={'Product': 'CPUname'}, inplace=True)
        tab(ex_CPU.head())
        ex_CPU_name = ex_CPU['CPUname']
        print(f"above table has {ex_CPU.shape[0]} rows")
        self.CPUBench.rename(columns={'cpuName': 'CPUname'}, inplace=True)
        tab(self.CPUBench.head())
        CPUBench_name = self.CPUBench['CPUname']
        print(f"above table has {self.CPUBench.shape[0]} rows")
        # ex_CPU has fewer rows, so that will be the base to minimize missing data
        # The CPU naming scheme used in ex_CPU will be used, and so the names used in CPUBench must be normalized to match them
        table_names = pd.concat([ex_CPU_name.reset_index(drop=True),CPUBench_name.reset_index(drop=True)],axis=1)
        tab(table_names.head())
        table_names.to_csv("temp/merged_name_data.csv",index=False)
        # separate intel cpus from AMD
        intel_data_1 = ex_CPU[ex_CPU['CPUname'].str.contains('Intel', case=False, na=False)].copy()
        intel_data_1.to_csv('temp/intel_data_1.csv',index=False)
        intel_data_2 = self.CPUBench[self.CPUBench['CPUname'].str.contains('Intel', case=False, na=False)].copy()
        # The next line removes the '@x.xxGHz' from the end of any of the names that have that
        intel_data_2.loc[:, 'CPUname'] = intel_data_2['CPUname'].str.replace(r' @.*GHz', '', regex=True)
        intel_data_2.to_csv('temp/intel_data_2.csv',index=False)
        merged_df = pd.merge(intel_data_1, intel_data_2, on='CPUname', how='inner')
        merged_df.to_csv('temp/intel_cpu_merged.csv',index=False)

        









    def get_attribute_name(self, attribute):
        for name, value in self.__dict__.items():
            if value is attribute:
                return name
        return None


def main():

    dav = DAV_Project_Driver()


def tab(str):
    print(tabulate(str, tablefmt='pretty', headers='keys'))

if __name__ == '__main__':
    main()