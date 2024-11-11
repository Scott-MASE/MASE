import pandas as pd
from tabulate import tabulate

class DAV_Project_Driver:
    def __init__(self):
        url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/All_GPUs.csv"
        self.dfGPU = pd.read_csv(url)
        url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/Intel_CPUs.csv"
        self.dfCPU = pd.read_csv(url)
        url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/AMD_cpubenchmarks.csv"
        self.dfAMD_CPU_Bench = pd.read_csv(url)  # UserBenchmark
        url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/chip_dataset.csv"
        self.dfGPU_CPU = pd.read_csv(url)
        url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/CPU_benchmark_v4.csv"
        self.dfCPU_Bench = pd.read_csv(url)  # Passmark
        url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/CPU_r23_v2.csv"
        self.dfCPU_Bench_2 = pd.read_csv(url)  # Cinebench R23
        self.F_GPUData = None
        self.F_CPUData = None





        print("\nHeaders of raw data:\n")
        # print("Table: dfGPU")
        # print(tabulate(self.dfGPU.head(), headers='keys', tablefmt='pretty'))
        print("\n------------------------------------------------")
        print("\nTable: dfCPU")
        print(tabulate(self.dfCPU.head(), headers='keys', tablefmt='pretty'))
        print("\n------------------------------------------------")
        print("\nTable: dfAMD_CPU_Bench")
        print(tabulate(self.dfAMD_CPU_Bench.head(), headers='keys', tablefmt='pretty'))
        print("\n------------------------------------------------")
        # print("\nTable: dfGPU_CPU")
        # print(tabulate(self.dfGPU_CPU.head(), headers='keys', tablefmt='pretty'))
        print("\n------------------------------------------------")
        print("\nTable: dfCPU_Bench")
        print(tabulate(self.dfCPU_Bench.head(), headers='keys', tablefmt='pretty'))
        print("\n------------------------------------------------")
        print("\nTable: dfCPU_Bench_2")
        print(tabulate(self.dfCPU_Bench_2.head(), headers='keys', tablefmt='pretty'))


        # Load each DataFrame if not already loaded

        Combined_CPUData = pd.merge(self.dfCPU, self.dfAMD_CPU_Bench, left_on='Processor_Number', right_on='Model', how='outer')


        self.F_CPUData = pd.merge(Combined_CPUData, self.dfCPU_Bench, left_on='Processor_Number', right_on='Processor', how='outer')

        print(tabulate(self.F_CPUData.head(),tablefmt='pretty', headers='keys'))


