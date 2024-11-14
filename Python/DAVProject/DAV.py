import pandas as pd
from tabulate import tabulate


class DAV_Project_Driver:
    def __init__(self):
        self.data = []

        # url = 'data/All_GPUs.csv'
        # self.dfGPU = pd.read_csv(url)
        # self.data.append(self.dfGPU)
        url = 'data/AMD_cpubenchmarks.csv'
        self.dfCPU = pd.read_csv(url)
        self.data.append(self.dfCPU)
        url = 'data/chip_dataset.csv'
        self.dfAMD_CPU_Bench = pd.read_csv(url)  # UserBenchmark
        self.data.append(self.dfAMD_CPU_Bench)
        # url = 'data/CPU_benchmark_v4.csv'
        # self.dfGPU_CPU = pd.read_csv(url)
        # self.data.append(self.dfGPU_CPU)
        url = 'data/CPU_r23_v2.csv'
        self.dfCPU_Bench = pd.read_csv(url)  # Passmark
        self.data.append(self.dfCPU_Bench)
        url = 'data/Intel_CPUs.csv'
        self.dfCPU_Bench_2 = pd.read_csv(url)  # Cinebench R23
        self.data.append(self.dfCPU_Bench_2)
        self.F_GPUData = None
        self.F_CPUData = None

        print("\nOverview of raw data:")
        self.overview()
        print("\nCleaning data...")
        self.cleanData()

    def cleanData(self):
        dfCPU = self.dfCPU[['Model']].reset_index(drop=True)
        dfAMD_CPU_Bench = self.dfAMD_CPU_Bench[['Product']].reset_index(drop=True)
        dfCPU_Bench = self.dfCPU_Bench[['cpuName']].reset_index(drop=True)
        dfCPU_Bench_2 = self.dfCPU_Bench_2[['Processor_Number']].reset_index(drop=True)

        # Find the maximum length among the DataFrames
        max_len = max(len(dfCPU), len(dfAMD_CPU_Bench), len(dfCPU_Bench), len(dfCPU_Bench_2))

        # Reindex each DataFrame to match the maximum length, filling with NaN where necessary
        dfCPU = dfCPU.reindex(range(max_len))
        dfAMD_CPU_Bench = dfAMD_CPU_Bench.reindex(range(max_len))
        dfCPU_Bench = dfCPU_Bench.reindex(range(max_len))
        dfCPU_Bench_2 = dfCPU_Bench_2.reindex(range(max_len))

        # Concatenate the DataFrames side by side
        merged = pd.concat([dfCPU, dfAMD_CPU_Bench, dfCPU_Bench, dfCPU_Bench_2], axis=1)
        merged.columns = ['Processor_Number', 'Model', 'cpuName1', 'cpuName2']
        merged.to_csv("data/merged_data.csv", index=False)

        print(merged.head())



    def overview(self):
        for n in self.data:
            name = self.get_attribute_name(n)
            print(f"\nTable: {name}")
            self.tab(n.head())


    def tab(self, str):
        print(tabulate(str, tablefmt='pretty', headers='keys'))

    def get_attribute_name(self, attribute):
        for name, value in self.__dict__.items():
            if value is attribute:
                return name
        return None


