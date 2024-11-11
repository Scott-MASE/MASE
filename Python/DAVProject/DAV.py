import pandas as pd
from tabulate import tabulate


class DAV_Project_Driver:
    def __init__(self):
        self.data = []
        # url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/All_GPUs.csv"
        # self.dfGPU = pd.read_csv(url)
        # self.data.append(self.dfGPU)
        url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/Intel_CPUs.csv"
        self.dfCPU = pd.read_csv(url)
        self.data.append(self.dfCPU)
        # url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/AMD_cpubenchmarks.csv"
        # self.dfAMD_CPU_Bench = pd.read_csv(url)  # UserBenchmark
        # self.data.append(self.dfAMD_CPU_Bench)
        # url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/chip_dataset.csv"
        # self.dfGPU_CPU = pd.read_csv(url)
        # self.data.append(self.dfGPU_CPU)
        # url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/CPU_benchmark_v4.csv"
        # self.dfCPU_Bench = pd.read_csv(url)  # Passmark
        # self.data.append(self.dfCPU_Bench)
        # url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/CPU_r23_v2.csv"
        # self.dfCPU_Bench_2 = pd.read_csv(url)  # Cinebench R23
        # self.data.append(self.dfCPU_Bench_2)
        self.F_GPUData = None
        self.F_CPUData = None

        print("\nOverview of raw data:")
        self.overview()
        print("\nCleaning data...")
        self.cleanData()

    def cleanData(self):
        self.tab(self.dfCPU[['Processor_Number','Product_Collection']])

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


