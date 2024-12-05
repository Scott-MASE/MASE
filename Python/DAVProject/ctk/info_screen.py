import customtkinter as ctk
import pandas as pd

# Load the DataFrame
df1 = pd.read_csv("/Users/scott/Documents/GitHub/MASE/Python/DAVProject/Temp/F_CPU_Data.csv")
df2 = pd.read_csv("/Users/scott/Documents/GitHub/MASE/Python/DAVProject/Temp2/F_GPU_Data.csv")

# This screen is fairly self-explanatory
description1 = {
    "ID": "Unique identifier for the dataset entry.",
    "CPUname": "The model name of the CPU.",
    "Type": "The type of processor (e.g., Desktop, Mobile, Server).",
    "Release Date": "The official release date of the CPU.",
    "Process Size (nm)": "The manufacturing process size of the CPU chip, measured in nanometers.",
    "TDP (W)": "Thermal Design Power of the CPU, indicating power draw in watts.",
    "Die Size (mm^2)": "The physical size of the CPU die, measured in square millimeters.",
    "Transistors (million)": "The number of transistors in the CPU, measured in millions.",
    "Freq (MHz)": "The base clock frequency of the CPU, measured in MHz.",
    "Foundry": "The semiconductor company that manufactured the CPU chip.",
    "Vendor": "The brand selling the CPU, often the same as the manufacturer.",
    "price": "The market price of the CPU, typically in USD.",
    "cpuMark": "Overall benchmark score for the CPU's performance.",
    "cpuValue": "The performance-to-price ratio of the CPU.",
    "threadMark": "The benchmark score specifically for multi-threaded tasks.",
    "threadValue": "The performance-to-price ratio for multi-threaded tasks.",
    "TDP": "Duplicate column for Thermal Design Power in watts.",
    "powerPerf": "The power efficiency of the CPU, measured as performance per watt.",
    "cores": "The number of physical cores in the CPU.",
    "testDate": "The date when the CPU was tested for benchmarking.",
    "socket": "The type of socket required by the CPU (e.g., LGA1200, AM4).",
    "category": "The intended use case or category of the CPU (e.g., Gaming, Workstation, Server)."
}

description2 = {
    "ID": "Unique identifier for the dataset entry.",
    "Architecture": "The GPU architecture, defining its core design.",
    "Best_Resolution": "The maximum supported resolution for optimal performance.",
    "Core_Speed": "The clock speed of the GPU core, measured in MHz.",
    "Direct_X": "The highest DirectX version supported by the GPU.",
    "Integrated": "Indicates whether the GPU is integrated into the CPU (Yes/No).",
    "L2_Cache": "Size of the GPU's L2 cache memory, typically in KB.",
    "Manufacturer": "The company that produced the GPU (e.g., NVIDIA, AMD).",
    "Max_Power": "The maximum power consumption of the GPU, measured in watts.",
    "Memory": "The total video memory available on the GPU, typically in GB.",
    "Memory_Bandwidth": "The memory transfer rate, measured in GB/s.",
    "Memory_Bus": "Width of the memory bus, indicating data throughput capability.",
    "Memory_Speed": "The clock speed of the GPU memory, measured in MHz.",
    "Memory_Type": "The type of memory used in the GPU (e.g., GDDR6, HBM2).",
    "GPUname": "The model name of the GPU.",
    "Notebook_GPU": "Indicates if the GPU is designed for notebooks/laptops (Yes/No).",
    "Open_GL": "The highest OpenGL version supported by the GPU.",
    "PSU": "Recommended power supply unit for the GPU, measured in watts.",
    "Pixel_Rate": "The number of pixels the GPU can render per second, in GPixel/s.",
    "Power_Connector": "The type of power connector required by the GPU (e.g., 6-pin, 8-pin).",
    "Process": "The manufacturing process size of the GPU chip, measured in nanometers.",
    "ROPs": "The number of Render Output Units in the GPU.",
    "Release_Date": "The official release date of the GPU.",
    "Release_Price": "The initial price of the GPU at launch, typically in USD.",
    "Resolution_WxH": "The maximum supported resolution, expressed as width x height.",
    "Shader": "The number of shader cores or processing units in the GPU.",
    "TMUs": "The number of Texture Mapping Units in the GPU.",
    "Texture_Rate": "The GPU's texture processing rate, measured in GTexels/s.",
    "VGA_Connection": "Indicates if the GPU supports VGA output (Yes/No).",
    "Release Date": "Duplicate column for release date (consider merging).",
    "Process Size (nm)": "Duplicate of 'Process', specifying chip size in nanometers.",
    "TDP (W)": "Thermal Design Power of the GPU, indicating power draw in watts.",
    "Die Size (mm^2)": "The physical size of the GPU die, measured in square millimeters.",
    "Transistors (million)": "The number of transistors in the GPU, measured in millions.",
    "Freq (MHz)": "The frequency of operation, typically the clock speed in MHz.",
    "Foundry": "The semiconductor company that manufactured the GPU chip.",
    "Vendor": "The brand selling the GPU, often the same as the manufacturer.",
    "FP32 GFLOPS": "The GPU's FP32 floating-point performance, measured in GFLOPS.",
    "FP64 GFLOPS": "The GPU's FP64 floating-point performance, measured in GFLOPS."
}


intro = "Welcome to the Info Page.\nThis page gives a brief description of each column of the datasets."


def info_screen():
    info_screen = ctk.CTk()
    info_screen.geometry("800x600")
    info_screen.title("Info Page")

    # Creating the top text box
    top_text_frame = ctk.CTkFrame(info_screen)
    top_text_frame.pack(side="top", fill="x", padx=10, pady=10)

    # Adding the intro to the top text box
    info_label = ctk.CTkTextbox(top_text_frame, font=("Arial", 18), height=60)
    info_label.pack(pady=10, expand=True, fill="both")
    info_label.insert("1.0", intro)  # Insert the intro text
    info_label.configure(state="disabled")  # Make it read-only

    # Button frame for the 2 buttons to switch between CPU and GPU info
    button_frame = ctk.CTkFrame(info_screen)
    button_frame.pack(side="top", pady=10)

    # makes a scrollable frame for the info, too long to fit everything on screen
    text_frame = ctk.CTkFrame(info_screen)
    text_frame.pack(side="top", fill="both", expand=True, padx=10, pady=10)

    # scrollable area
    text_area = ctk.CTkTextbox(text_frame, wrap="word", font=("Arial", 14))
    text_area.pack(fill="both", expand=True, padx=5, pady=5)

    # this function swaps out the info text when the df is swapped
    def update_text(df,descriptions):
        text_area.configure(state="normal") #disable read only
        text_area.delete("1.0", "end")  # clears existing text
        for column in df.columns:
            text_area.insert("end", f"{column}:\n{descriptions.get(column)}\n\n")
        text_area.configure(state="disabled") #enable read only again

    # default descriptions done for CPU df
    update_text(df1,description1)

    # Buttons to toggle descriptions
    button1 = ctk.CTkButton(
        button_frame,
        text="Show CPU dataset Info",
        command=lambda: update_text(df1,description1)
    )
    button1.pack(side="left", padx=20)

    button2 = ctk.CTkButton(
        button_frame,
        text="Show GPU dataset Info",
        command=lambda: update_text(df2,description2)
    )
    button2.pack(side="left", padx=20)

    info_screen.mainloop()



