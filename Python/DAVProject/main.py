import pandas as pd

url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/All_GPUs.csv"
dfGPU = pd.read_csv(url)
url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/Intel_CPUs.csv"
dfCPU = pd.read_csv(url)
url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/AMD_cpubenchmarks.csv"
dfAMD_CPU_Bench = pd.read_csv(url) #UserBenchmark
url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/chip_dataset.csv"
dfGPU_CPU = pd.read_csv(url)
url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/CPU_benchmark_v4.csv"
dfCPU_Bench = pd.read_csv(url) #Passmark
url = "https://raw.githubusercontent.com/Scott-MASE/HostedData/main/CPU_r23_v2.csv"
dfCPU_Bench_2 = pd.read_csv(url) #Cinebench R23


print("\nHeaders of raw data:\n")
print("Table: dfGPU")
print(dfGPU.head())
print("\n------------------------------------------------")
print("\nTable: dfCPU")
print(dfCPU.head())
print("\n------------------------------------------------")
print("\nTable: dfAMD_CPU_Bench")
print(dfAMD_CPU_Bench.head())
print("\n------------------------------------------------")
print("\nTable: dfGPU_CPU")
print(dfGPU_CPU.head())
print("\n------------------------------------------------")
print("\nTable: ddfCPU_Bench")
print(dfCPU_Bench.head())
print("\n------------------------------------------------")
print("\nTable: dfCPU_Bench_2")
print(dfCPU_Bench_2.head())
