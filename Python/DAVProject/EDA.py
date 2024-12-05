import pandas as pd

CPU = "Temp/F_CPU_Data.csv"
GPU = "Temp2/F_GPU_Data.csv"

def performEDA():
    CPUdf = pd.read_csv(CPU)
    GPUdf = pd.read_csv(GPU)
    print(CPUdf.head())



performEDA()