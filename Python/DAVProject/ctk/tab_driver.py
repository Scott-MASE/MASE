import customtkinter as ctk
from tkinter import ttk
from ctk.DAV_screens.graph_tab import create_graph_tab  # Import your custom graph tab function
from ctk.DAV_screens.table_tab import create_table_tab
from ctk.DAV_screens.CPU_comparison_tab import create_cpu_comparison_tab
from ctk.DAV_screens.GPU_comparison_tab import create_gpu_comparison_tab


# all the tabs in DAV_screens are initialized here
def tab_driver():
    tab_driver = ctk.CTk()
    tab_driver.geometry("1500x900")
    tab_driver.title("Tab Application")

    # creates new tabbed notebook
    notebook = ttk.Notebook(tab_driver)
    notebook.pack(fill="both", expand=True)

    # creates graph tab
    graph_tab = ctk.CTkFrame(notebook)
    create_graph_tab(graph_tab)
    notebook.add(graph_tab, text="Graph")


    table_tab = ctk.CTkFrame(notebook)
    create_table_tab(table_tab)
    notebook.add(table_tab, text="Table")

    cpu_comparison_tab = ctk.CTkFrame(notebook)
    create_cpu_comparison_tab(cpu_comparison_tab)
    notebook.add(cpu_comparison_tab, text="CPU Comparison")

    gpu_comparison_tab = ctk.CTkFrame(notebook)
    create_gpu_comparison_tab(gpu_comparison_tab)
    notebook.add(gpu_comparison_tab, text="GPU Comparison")

    tab_driver.mainloop()


