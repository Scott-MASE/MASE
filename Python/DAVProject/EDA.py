import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

CPU = "Temp/F_CPU_Data.csv"
GPU = "Temp2/F_GPU_Data.csv"
CPUdf = pd.read_csv(CPU)
GPUdf = pd.read_csv(GPU)

def performEDA():
    print("CPUdf header and cols\n--------------------------------------------------------------------------")
    print(CPUdf.head())
    print(CPUdf.columns)
    print("\nGPUdf header and cols\n--------------------------------------------------------------------------")
    print(GPUdf.head())
    print(GPUdf.columns)
    plt_1()

def plt_1():
    # Convert 'Release Date' to a datetime object if it's not already
    global CPUdf
    CPUdf['Release Date'] = pd.to_datetime(CPUdf['Release Date'])

    # Sort the DataFrame by 'Release Date' to ensure proper trend visualization
    CPUdf = CPUdf.sort_values('Release Date')

    # Create a figure for the plots
    plt.figure(figsize=(14, 8))

    # Plot for Price over Time
    sns.lineplot(x='Release Date', y='price', data=CPUdf, label='Price ($)', color='blue', linewidth=2)

    # Plot for Process Size over Time (scaled for visualization if needed)
    sns.lineplot(x='Release Date', y='Process Size (nm)', data=CPUdf, label='Process Size (nm)', color='green',
                 linewidth=2)

    # Add titles and labels
    plt.title("Trends in Price and Process Size Over Time", fontsize=16)
    plt.xlabel("Release Date", fontsize=12)
    plt.ylabel("Value", fontsize=12)
    plt.legend(title="Metric", fontsize=10)
    plt.grid(True)

    # Display the plot
    plt.show()



performEDA()