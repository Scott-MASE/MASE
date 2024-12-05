import customtkinter as ctk
from tkinter import ttk, filedialog
import pandas as pd
from functools import partial

CPU_CSV_FILE_PATH = "Temp/F_CPU_Data.csv"
GPU_CSV_FILE_PATH = "Temp2/F_GPU_Data.csv"

cpu_df = pd.read_csv(CPU_CSV_FILE_PATH)
gpu_df = pd.read_csv(GPU_CSV_FILE_PATH)

if not pd.api.types.is_datetime64_any_dtype(cpu_df['Release Date']):
    cpu_df['Release Date'] = pd.to_datetime(cpu_df['Release Date'], errors='coerce')
if not pd.api.types.is_datetime64_any_dtype(gpu_df['Release Date']):
    gpu_df['Release Date'] = pd.to_datetime(gpu_df['Release Date'], errors='coerce')

cpu_df = cpu_df.sort_values(by='ID', ascending=True).reset_index(drop=True)
gpu_df = gpu_df.sort_values(by='ID', ascending=True).reset_index(drop=True)

#this tab contains a table which can switch between the CPU or GPU df, and export it to a csv

def sort_column(tree, column, df):
    current_sort_order = tree.heading(column, 'text').endswith('↓')  # Check current sort order
    ascending = not current_sort_order  # Toggle order

    df_sorted = df.sort_values(by=column, ascending=ascending)

    # Update the column header text to show sort direction
    for col in df.columns:
        tree.heading(col, text=col)
    sort_indicator = "↓" if not ascending else "↑"
    tree.heading(column, text=f"{column} {sort_indicator}")

    # Update the treeview with sorted data
    for row in tree.get_children():
        tree.delete(row)
    for _, row in df_sorted.iterrows():
        tree.insert("", "end", values=list(row))

    return df_sorted

# swaps out the table data when the df is changed
def update_treeview(tree, df):
    tree.delete(*tree.get_children())  # clear existing rows

    # update columns
    tree["columns"] = list(df.columns)
    for col in df.columns:
        tree.heading(col, text=col, command=partial(sort_column, tree, col, df))
        max_length = max(df[col].astype(str).apply(len).max(), len(col))
        tree.column(col, anchor="center", width=max_length * 10, minwidth=30, stretch=False)

    # insert new rows
    for _, row in df.iterrows():
        tree.insert("", "end", values=list(row))

# function to export the df to a CSV file
def export_to_csv(current_df):
    file_path = filedialog.asksaveasfilename(
        defaultextension=".csv",
        filetypes=[("CSV files", "*.csv"), ("All files", "*.*")]
    )
    if file_path:
        try:
            current_df.to_csv(file_path, index=False)
            print(f"File saved successfully at {file_path}")
        except Exception as e:
            print(f"An error occurred: {e}")


def create_table_tab(parent):

    table_frame = ctk.CTkFrame(parent)
    table_frame.pack(fill="both", expand=True, padx=10, pady=10)

    # dict to keep track of the current df
    current_df = {'df': cpu_df}

    # buttons for CPU, GPU, and Export
    button_frame = ctk.CTkFrame(table_frame)
    button_frame.pack(pady=10, side="top", fill="x")

    button_container = ctk.CTkFrame(button_frame)
    button_container.pack(anchor="center")

    def show_cpu():
        current_df['df'] = cpu_df
        update_treeview(tree, cpu_df)

    def show_gpu():
        current_df['df'] = gpu_df
        update_treeview(tree, gpu_df)

    cpu_button = ctk.CTkButton(button_container, text="CPU", command=show_cpu)
    gpu_button = ctk.CTkButton(button_container, text="GPU", command=show_gpu)
    export_button = ctk.CTkButton(button_container, text="Export to CSV", command=lambda: export_to_csv(current_df['df']))

    cpu_button.pack(side="left", padx=5)
    gpu_button.pack(side="left", padx=5)
    export_button.pack(side="left", padx=5)

    # Subframe for the Treeview and vertical scrollbar
    sub_frame = ttk.Frame(table_frame)
    sub_frame.pack(fill="both", expand=True)

    tree = ttk.Treeview(sub_frame, columns=list(cpu_df.columns), show='headings', selectmode="browse")
    tree.grid(row=0, column=0, sticky="nsew")  # Use grid for precise placement

    # init tree with CPU df
    update_treeview(tree, cpu_df)

    # Add vertical scrollbar
    v_scrollbar = ttk.Scrollbar(sub_frame, orient="vertical", command=tree.yview)
    v_scrollbar.grid(row=0, column=1, sticky="ns")  # Attach to the right side
    tree.configure(yscrollcommand=v_scrollbar.set)

    # Add horizontal scrollbar
    h_scrollbar = ttk.Scrollbar(table_frame, orient="horizontal", command=tree.xview)
    h_scrollbar.pack(side="bottom", fill="x")
    tree.configure(xscrollcommand=h_scrollbar.set)

    # Ensure the Treeview widget and scrollbars resize correctly
    sub_frame.grid_rowconfigure(0, weight=1)
    sub_frame.grid_columnconfigure(0, weight=1)

    return parent
