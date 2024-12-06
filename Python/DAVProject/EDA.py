import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

import numpy as np
from sklearn.linear_model import LinearRegression



def performEDA():
    CPU = "Temp/F_CPU_Data.csv"
    GPU = "Temp2/F_GPU_Data.csv"
    CPUdf = pd.read_csv(CPU)
    GPUdf = pd.read_csv(GPU)
    print("CPUdf header and cols\n--------------------------------------------------------------------------")
    print(CPUdf.head())
    print(CPUdf.columns)
    print("\nGPUdf header and cols\n--------------------------------------------------------------------------")
    print(GPUdf.head())
    print(GPUdf.columns)
    desc = [
        'count',
        'mean',
        'std',
        'min',
        '25%',
        '50%',
        '75%',
        'max'
    ]
    CPUdesc = CPUdf.describe().round(2)
    CPUdesc.insert(0, 'Description', desc)
    CPUdesc.to_csv("Temp3/CPU_Data_desc.csv", index=False)
    for col in CPUdf.columns:
        print(f"Description for column: {col}")
        print(CPUdf[col].describe(), end="\n\n")
    GPUdesc = GPUdf.describe().round(2)
    GPUdesc.insert(0, 'Description', desc)
    GPUdesc.to_csv("Temp3/GPU_Data_desc.csv", index=False)
    for col in CPUdf.columns:
        print(f"Description for column: {col}")
        print(CPUdf[col].describe(), end="\n\n")
    plt_1()
    plot_2()
    plt_3()
    plt4()

def plt_1():
    CPU = "Temp/F_CPU_Data.csv"
    GPU = "Temp2/F_GPU_Data.csv"
    CPUdf = pd.read_csv(CPU)
    GPUdf = pd.read_csv(GPU)
    # Convert 'Release Date' to a datetime object if it's not already
    df = CPUdf.copy()
    # Drop rows with invalid or missing dates
    df['Release Date'] = pd.to_datetime(df['Release Date'], errors='coerce')

    df = df.dropna(subset=['Release Date'])

    # Extract year for grouping
    df['Year'] = df['Release Date'].dt.year

    # Group by Year and calculate the average price
    yearly_avg_price = df.groupby('Year')['price'].mean().reset_index()

    # Prepare data for regression
    X = yearly_avg_price['Year'].values.reshape(-1, 1)  # Predictor (Year)
    y = yearly_avg_price['price'].values  # Target (Average Price)

    # Train a linear regression model
    model = LinearRegression()
    model.fit(X, y)

    # Predict prices for the next 5 years
    future_years = np.arange(yearly_avg_price['Year'].max() + 1, yearly_avg_price['Year'].max() + 6).reshape(-1, 1)
    future_prices = model.predict(future_years)

    # Combine actual and predicted data
    future_data = pd.DataFrame({'Year': future_years.flatten(), 'price': future_prices})
    extended_data = pd.concat([yearly_avg_price, future_data], ignore_index=True)

    # Plot the data
    plt.figure(figsize=(12, 6))
    plt.plot(yearly_avg_price['Year'], yearly_avg_price['price'], marker='o', label='Actual Data', linestyle='-')
    plt.plot(future_data['Year'], future_data['price'], marker='x', label='Predicted Data', linestyle='--', color='red')
    plt.title('Average CPU Price by Year with 5-Year Prediction')
    plt.xlabel('Year')
    plt.ylabel('Average Price')
    plt.grid()
    plt.xticks(np.arange(extended_data['Year'].min(), extended_data['Year'].max() + 1), rotation=45)
    plt.legend()
    plt.tight_layout()
    plt.savefig("images/CPU_price_prediction.png")
    plt.close()

def plot_2():
    return

def plt_3():
    CPU = "Temp/F_CPU_Data.csv"
    GPU = "Temp2/F_GPU_Data.csv"
    CPUdf = pd.read_csv(CPU)
    GPUdf = pd.read_csv(GPU)
    df = CPUdf.copy()
    numerical_cols = df.select_dtypes(include=['float64', 'int64']).drop(columns=['testDate','TDP','ID'], errors='ignore')

    # Calculate correlation matrix
    correlation_matrix = numerical_cols.corr()

    # Extract correlation of 'price' with other numerical columns
    price_correlation = correlation_matrix[['price']].drop('price')
    price_correlation = price_correlation.reindex(price_correlation['price'].abs().sort_values(ascending=False).index)

    # Plot the heatmap
    plt.figure(figsize=(8, len(price_correlation) * 0.5))  # Condensed plot
    sns.heatmap(
        price_correlation,
        annot=True,
        cmap='coolwarm',
        fmt=".2f",
        cbar=True,
        vmin=-1,  # Minimum correlation value
        vmax=1  # Maximum correlation value
    )
    plt.title('Correlation of CPU Price with relevant CPU Features')
    plt.tight_layout()
    plt.savefig("images/CPU_price_correlation.png")
    plt.close()

def plt4():
    CPU = "Temp/F_CPU_Data.csv"
    GPU = "Temp2/F_GPU_Data.csv"
    CPUdf = pd.read_csv(CPU)
    GPUdf = pd.read_csv(GPU)
    df = GPUdf.copy()
    numerical_cols = df.select_dtypes(include=['float64', 'int64']).drop(columns=['GPUname','VGA_Connection','Memory_Type','ID','Open_GL','Shader','Direct_X'], errors='ignore')
    correlation_matrix = numerical_cols.corr()


    # Extract correlation of 'price' with other numerical columns
    price_correlation = correlation_matrix[['Release Price']].drop('Release Price')
    price_correlation = price_correlation.reindex(price_correlation['Release Price'].abs().sort_values(ascending=False).index)

    # Plot the heatmap
    plt.figure(figsize=(8, len(price_correlation) * 0.5))  # Condensed plot
    sns.heatmap(
        price_correlation,
        annot=True,
        cmap='coolwarm',
        fmt=".2f",
        cbar=True,
        vmin=-1,  # Minimum correlation value
        vmax=1,  # Maximum correlation value
    )
    plt.title('Correlation of GPU Price with relevant GPU Features')
    plt.tight_layout()
    plt.savefig("images/GPU_price_correlation.png")
    plt.close()

