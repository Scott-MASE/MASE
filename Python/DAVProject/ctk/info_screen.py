from tkinter import ttk
import customtkinter as ctk
import pandas as pd

# Load the DataFrame


# Descriptions for CPU and GPU datasets
description1 = {
    "Description": "A brief description of the CPU model and its features.",
    "ID": "Unique identifier for the dataset entry.",
    "Process Size (nm)": "The manufacturing process size of the CPU chip, measured in nanometers. A smaller process size usually indicates better performance and energy efficiency.",
    "TDP (W)": "Thermal Design Power of the CPU, indicating power draw in watts.",
    "Die Size (mm^2)": "The physical size of the CPU die, measured in square millimeters.",
    "Transistors (million)": "The number of transistors in the CPU, measured in millions.",
    "Freq (MHz)": "The base clock frequency of the CPU, measured in MHz. It determines the number of cycles the CPU can perform per second.",
    "price": "The market price of the CPU, typically in USD.",
    "cpuMark": "Overall benchmark score for the CPU's performance, usually from synthetic benchmarking tools.",
    "cpuValue": "The performance-to-price ratio of the CPU, indicating how much performance you get per unit of currency.",
    "threadMark": "The benchmark score specifically for multi-threaded tasks, showing how well the CPU performs in parallel computing workloads.",
    "threadValue": "The performance-to-price ratio for multi-threaded tasks, indicating the value of the CPU for multi-core performance.",
    "TDP": "Duplicate column for Thermal Design Power in watts.",
    "powerPerf": "The power efficiency of the CPU, measured as performance per watt of power consumed.",
    "cores": "The number of physical cores in the CPU, which determines how many tasks the CPU can handle simultaneously.",
    "testDate": "The date when the CPU was tested for benchmarking, to provide context for performance comparisons over time.",
    "Transistor Density": "The number of transistors per unit of area on the CPU die, typically measured in millions of transistors per square millimeter.",
    "Performance per Watt": "The performance output relative to the power consumption, indicating the CPU's energy efficiency."
}

description2 = {
    "Description": "A brief description of the GPU model and its features.",
    "ID": "Unique identifier for the dataset entry.",
    "Best Resolution": "The highest resolution supported by the GPU, typically in pixels (e.g., 3840x2160 for 4K).",
    "Direct X": "The version of Microsoft's DirectX supported by the GPU, which determines compatibility with gaming and multimedia applications.",
    "L2 Cache": "The amount of Level 2 cache memory available on the GPU, which helps improve performance for tasks like texture mapping and shading.",
    "Max Power": "The maximum power consumption of the GPU under full load, typically measured in watts.",
    "Memory": "The total video memory available on the GPU, typically in GB.",
    "Memory Bandwidth": "The rate at which data can be read from or written to the GPU memory, typically measured in GB/s.",
    "Memory Bus": "The width of the memory interface, typically measured in bits (e.g., 256-bit). It affects the memory bandwidth.",
    "Memory Speed": "The speed at which the GPU memory operates, typically measured in MHz or GHz.",
    "Memory Type": "The type of memory used by the GPU (e.g., GDDR6, HBM2).",
    "Open GL": "The version of OpenGL supported by the GPU, a cross-platform graphics API for rendering 2D and 3D graphics.",
    "PSU": "Recommended power supply unit for the GPU, measured in watts.",
    "Pixel Rate": "The rate at which the GPU can render pixels, typically measured in pixels per second.",
    "Process": "The manufacturing process size of the GPU chip, measured in nanometers.",
    "ROPs": "The number of Render Output Units in the GPU, responsible for final image processing and output.",
    "Release Price": "The launch price of the GPU when it was initially released.",
    "Resolution WxH": "The supported resolution of the GPU in terms of width (W) and height (H), e.g., 1920x1080 for Full HD.",
    "Shader": "The number of shader cores or processing units in the GPU, which are responsible for executing the parallel computations for rendering.",
    "TMUs": "The number of Texture Mapping Units in the GPU, which are responsible for mapping textures onto 3D objects.",
    "Texture Rate": "The rate at which textures can be processed by the GPU, typically measured in gigatexels per second (GT/s).",
    "VGA Connection": "Indicates whether the GPU supports a VGA (Video Graphics Array) output connection.",
    "Process Size (nm)": "Duplicate of 'Process', specifying chip size in nanometers.",
    "TDP (W)": "Thermal Design Power of the GPU, indicating power draw in watts.",
    "Die Size (mm^2)": "The physical size of the GPU die, measured in square millimeters.",
    "Transistors (million)": "The number of transistors in the GPU, measured in millions.",
    "Freq (MHz)": "The frequency of operation, typically the clock speed in MHz.",
    "FP32 GFLOPS": "The GPU's FP32 floating-point performance, measured in GFLOPS (billion floating-point operations per second).",
    "FP64 GFLOPS": "The GPU's FP64 floating-point performance, measured in GFLOPS (billion floating-point operations per second).",
    "Release Date Rank": "A ranking or score based on the GPU's release date, indicating its relevance or importance within a specific period."
}

intro = "Welcome to the Info Page.\nThis page gives a brief description of each column of the datasets."


def info_screen():
    df1 = pd.read_csv("Temp3/CPU_Data_desc.csv")
    df2 = pd.read_csv("Temp3/GPU_Data_desc.csv")
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

    # Function to swap to the Treeview
    def switch_to_treeview(df):

        # Clear any existing widgets in the text frame
        for widget in text_frame.winfo_children():
            widget.destroy()

        # Create a frame for Treeview and scrollbar
        tree_frame = ctk.CTkFrame(text_frame)
        tree_frame.pack(fill="both", expand=True)

        # Create a vertical and horizontal scrollbar
        vsb = ttk.Scrollbar(tree_frame, orient="vertical")
        vsb.pack(side="right", fill="y")

        hsb = ttk.Scrollbar(tree_frame, orient="horizontal")
        hsb.pack(side="bottom", fill="x")

        # Create treeview
        tree = ttk.Treeview(tree_frame, columns=list(df.columns), show="headings", yscrollcommand=vsb.set, xscrollcommand=hsb.set)

        # Create columns
        for col in df.columns:
            tree.heading(col, text=col)
            tree.column(col, width=100, anchor="w")

        # Insert data into the treeview
        for _, row in df.iterrows():
            tree.insert("", "end", values=list(row))

        # Configure scrollbars
        vsb.config(command=tree.yview)
        hsb.config(command=tree.xview)

        tree.pack(fill="both", expand=True)

    # Function to update text area with descriptions
    def update_text(df, descriptions):
        for widget in text_frame.winfo_children():
            widget.destroy()

        # Create a text box for the descriptions
        text_area = ctk.CTkTextbox(text_frame, wrap="word", font=("Arial", 14))
        text_area.pack(fill="both", expand=True, padx=5, pady=5)

        for column in df.columns:
            text_area.insert("end", f"{column}:\n{descriptions.get(column)}\n\n")
        text_area.configure(state="disabled")

    # Default display is CPU descriptions
    update_text(df1, description1)

    # Buttons to toggle descriptions and tree views
    button1 = ctk.CTkButton(
        button_frame,
        text="CPU col descriptions",
        command=lambda: update_text(df1, description1)
    )
    button1.pack(side="left", padx=20)

    button2 = ctk.CTkButton(
        button_frame,
        text="GPU col descriptions",
        command=lambda: update_text(df2, description2)
    )
    button2.pack(side="left", padx=20)

    button3 = ctk.CTkButton(
        button_frame,
        text="CPU data details",
        command=lambda: switch_to_treeview(df1)
    )
    button3.pack(side="left", padx=20)

    button4 = ctk.CTkButton(
        button_frame,
        text="GPU data details",
        command=lambda: switch_to_treeview(df2)
    )
    button4.pack(side="left", padx=20)

    info_screen.mainloop()



