import customtkinter as ctk
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
import matplotlib.pyplot as plt
import pandas as pd
import mplcursors

# Matplotlib settings
plt.rcParams.update({
    "font.size": 7,
    "axes.titlesize": 9,
    "axes.labelsize": 9,
    "xtick.labelsize": 7,
    "ytick.labelsize": 7,
    "legend.fontsize": 7,
})







# this tab allows for the dynamic creation of a plot. you can switch between the GPU and CPU df,
# and select any number of columns to plot against release date.
def create_graph_tab(parent):
    # Constants
    DEFAULT_X = 'Release Date'
    CSV_FILE_PATH = "Temp/F_CPU_Data.csv"
    CSV_FILE_PATH2 = "Temp2/F_GPU_Data.csv"
    keys_to_skip = {"ID", "CPUname", "Type", "Foundry", "Vendor", "testDate", "socket", "category",
                    "Architecture", "Direct_X", "Integrated", "Manufacturer", "Power_Connector", "Process",
                    "VGA_Connection", "GPUname", "Notebook_GPU", "Shader", "Release_Date"}


    # Load data
    df1 = pd.read_csv(CSV_FILE_PATH)
    df2 = pd.read_csv(CSV_FILE_PATH2)
    for df in [df1, df2]:
        if not pd.api.types.is_datetime64_any_dtype(df[DEFAULT_X]):
            df[DEFAULT_X] = pd.to_datetime(df[DEFAULT_X], errors='coerce')

    df1 = df1.drop(columns=[col for col in keys_to_skip if col in df1.columns])
    df2 = df2.drop(columns=[col for col in keys_to_skip if col in df2.columns])
    current_df = {"df": df1}
    selected_y_cols = set()  # keeps track of the currently selected cols for graphing

    top_frame = ctk.CTkFrame(parent)
    top_frame.pack(side="top", fill="x", padx=10, pady=10)

    # container for all buttons and other stuff in top bar
    container_frame = ctk.CTkFrame(top_frame)
    container_frame.pack(anchor="center", pady=5)

    # makes tickbox section scrollable
    checkbox_frame = ctk.CTkScrollableFrame(parent, orientation="horizontal", height=60)
    checkbox_frame.pack(side="top", fill="x", padx=10, pady=5)

    bottom_frame = ctk.CTkFrame(parent)
    bottom_frame.pack(side="top", fill="both", expand=True, padx=10, pady=10)

    # creates graph with default x and y axis
    fig, ax = plt.subplots(figsize=(6, 4))
    plot_graph(
        ax,
        current_df["df"],
        DEFAULT_X,
        [],  # Start with no Y-axis columns selected
        current_df["df"][DEFAULT_X].min(),
        current_df["df"][DEFAULT_X].max()
    )

    # embed the above plot into the tab
    canvas = FigureCanvasTkAgg(fig, master=bottom_frame)
    canvas.draw()
    canvas.get_tk_widget().pack(fill="both", expand=True)

    # controls for start and end range
    start_entry = ctk.CTkEntry(container_frame, width=120)
    start_entry.pack(side="left", padx=5)
    start_entry.insert(0, str(df1[DEFAULT_X].min()))

    end_entry = ctk.CTkEntry(container_frame, width=120)
    end_entry.pack(side="left", padx=5)
    end_entry.insert(0, str(df1[DEFAULT_X].max()))

    error_label = ctk.CTkLabel(container_frame, text="", text_color="red")
    error_label.pack(side="left", padx=10)

    # whenever the df is changed the graph is updated
    def update_graph():
        try:
            start_value = pd.to_datetime(start_entry.get(), errors="coerce")
            end_value = pd.to_datetime(end_entry.get(), errors="coerce")
            selected_y_cols_list = list(selected_y_cols)

            plot_graph(
                ax,
                current_df["df"],
                DEFAULT_X,
                selected_y_cols_list,
                start_value,
                end_value
            )
            canvas.draw()
            error_label.configure(text="")
        except Exception as e:
            error_label.configure(text=f"Error: {e}")

    # automatically updates graph when min and max x-axis are changed
    start_entry.bind("<KeyRelease>", lambda event: update_graph())
    end_entry.bind("<KeyRelease>", lambda event: update_graph())

    # updates checkboxes when df changed
    def update_checkboxes():
        for widget in checkbox_frame.winfo_children():
            widget.destroy()

        for col in current_df["df"].columns:
            if col != DEFAULT_X:
                checkbox = ctk.CTkCheckBox(
                    checkbox_frame,
                    text=col,
                    command=lambda c=col: toggle_y_col(c)
                )
                checkbox.pack(side="left", padx=5, pady=2)

    # Toggle selected columns
    def toggle_y_col(column):
        if column in selected_y_cols:
            selected_y_cols.remove(column)
        else:
            selected_y_cols.add(column)
        update_graph()

    # switch df and update controls
    def switch_dataframe(selected):
        current_df["df"] = df1 if selected == "CPU Data" else df2
        start_entry.delete(0, "end")
        start_entry.insert(0, str(current_df["df"][DEFAULT_X].min()))
        end_entry.delete(0, "end")
        end_entry.insert(0, str(current_df["df"][DEFAULT_X].max()))
        update_checkboxes()
        update_graph()

    # dropdown to switch DataFrames
    df_options = ctk.StringVar(value="CPU Data")
    df_menu = ctk.CTkOptionMenu(container_frame, variable=df_options, values=["CPU Data", "GPU Data"], command=switch_dataframe)
    df_menu.pack(side="left", padx=5)

    # button to reset all checkboxes
    def reset_checkboxes():
        nonlocal selected_y_cols
        selected_y_cols.clear()
        update_checkboxes()
        update_graph()

    reset_checkboxes_button = ctk.CTkButton(container_frame, text="Reset Checkboxes", command=reset_checkboxes)
    reset_checkboxes_button.pack(side="left", padx=5)

    # button to reset the date range
    def reset_range():
        start_entry.delete(0, "end")
        end_entry.delete(0, "end")
        start_entry.insert(0, str(current_df["df"][DEFAULT_X].min()))
        end_entry.insert(0, str(current_df["df"][DEFAULT_X].max()))
        update_graph()

    reset_range_button = ctk.CTkButton(container_frame, text="Reset Range", command=reset_range)
    reset_range_button.pack(side="left", padx=5)

    update_checkboxes()  # Initialize checkboxes
    return parent

# Plot graph function
def plot_graph(ax, df, x_col, y_cols, start_value, end_value):
    if not y_cols:
        ax.clear()
        ax.set_title("Select at least one column for Y-axis")
        ax.grid(True)
        return

    filtered_df = df[
        (df[x_col] >= start_value) & (df[x_col] <= end_value)
    ].sort_values(by=x_col)

    ax.clear()
    for y_col in y_cols:
        ax.scatter(
            filtered_df[x_col], filtered_df[y_col], marker="o", label=y_col
        )

    ax.set_title("Interactive Graph")
    ax.set_xlabel(x_col)
    ax.set_ylabel("Values")
    ax.grid(True)
    ax.legend()

    #this allows you to mouse over data points to see their exact values
    mplcursors.cursor(ax, hover=True).connect(
        "add", lambda sel: sel.annotation.set_text(
            f"{x_col}: {sel.target[0]}\n{y_cols}: {sel.target[1]}"
        )
    )
