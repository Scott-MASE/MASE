import customtkinter as ctk
import tkinter as tk
import pandas as pd
from functools import partial

df = pd.read_csv("Temp2/F_GPU_Data.csv")
leftVals = {}
rightVals = {}

# more or less identical to the cpu comparison tab
def search_gpu(entry, result_text_widget, df, result_dict):
    gpu_name = entry.get()
    result = df[df["GPUname"].str.contains(gpu_name, case=False, na=False)]

    result_text_widget.delete("1.0", tk.END)

    if not result.empty:
        num_rows = len(result.columns) + 2
        buffer = 2
        new_height = num_rows + buffer
        result_text_widget.config(height=new_height)

        header = "{:<25} {:<23}\n".format("Column", "Value")
        separator = "-" * 50 + "\n"
        result_text_widget.insert(tk.END, header)
        result_text_widget.insert(tk.END, separator)

        for col in df.columns:
            print(type(result[col].values[0]))
            row = "{:<25} {:<23}\n".format(col, result[col].values[0])
            result_text_widget.insert(tk.END, row)


            result_dict[col] = result[col]

    else:
        result_text_widget.config(height=5)
        result_text_widget.insert(tk.END, "No matching GPU found.")


def create_difference_table(leftVals, rightVals, result_text_widget):

    num_rows = len(leftVals) + 2
    buffer = -12
    new_height = num_rows + buffer
    result_text_widget.config(height=new_height)


    header = "{:<25} {:<10} {:<10} \n".format("Column", "Diff", "Percent Diff")
    separator = "-" * 50 + "\n"
    result_text_widget.delete("1.0", tk.END)
    result_text_widget.insert(tk.END, header)
    result_text_widget.insert(tk.END, separator)

    if leftVals != {} and rightVals != {}:
        keys_to_skip = {"ID", "Architecture","Direct_X","Integrated","Manufacturer","Power_Connector","Process",
                        "VGA_Connection","Release_Date","Foundry","Vendor","GPUname","Notebook_GPU","Shader"}




        for col in leftVals.keys():
            left_val = leftVals[col].values[0]
            right_val = rightVals[col].values[0]

            diff = "N/A"
            percDiff = "N/A"
            if col in keys_to_skip:
                continue


            try:
                if str(left_val).lower() not in {"nan", "none", ""} and str(right_val).lower() not in {"nan", "none",
                                                                                                       ""}:
                    diff = round(float(right_val) - float(left_val), 2)
                    percDiff = f"{round((float(right_val) - float(left_val)) / float(left_val) * 100, 2)}%"
            except (ValueError, TypeError):
                pass

            row = "{:<25} {:<10} {:<10}\n".format(col, diff, percDiff)
            result_text_widget.insert(tk.END, row)

    else:
        result_text_widget.insert(tk.END, "No comparison available.")



def autocomplete(entry, listbox, suggestions, result_label, event=None):
    typed_text = entry.get().lower()
    listbox.delete(0, tk.END)

    matches = [gpu for gpu in suggestions if gpu.lower().startswith(typed_text)]

    for match in matches:
        listbox.insert(tk.END, match)

    if not matches:
        listbox.pack_forget()
    else:
        listbox.pack(padx=10, pady=5, fill="x")



def on_suggestion_click(entry, listbox, suggestions, result_label, event=None):
    selected_item = listbox.get(listbox.curselection())
    entry.delete(0, tk.END)
    entry.insert(0, selected_item)

    listbox.pack_forget()

    search_gpu(entry, result_label, df, result_label)


def create_autocomplete_searchbox(frame, label_text, df, result_text_widget, result_dict):
    search_label = ctk.CTkLabel(frame, text=label_text)
    search_label.pack(padx=10, pady=5)

    search_entry = ctk.CTkEntry(frame, width=300)
    search_entry.pack(padx=10, pady=5)

    suggestion_listbox = tk.Listbox(frame, height=10, width=40)
    suggestion_listbox.pack_forget()


    search_entry.bind('<KeyRelease>',
                      partial(autocomplete, search_entry, suggestion_listbox, df["GPUname"].tolist(), result_text_widget))

    suggestion_listbox.bind('<ButtonRelease-1>',
                            partial(on_suggestion_click, search_entry, suggestion_listbox, df["GPUname"].tolist(),
                                    result_text_widget))


    search_button = ctk.CTkButton(frame, text="Search", command=partial(search_gpu, search_entry, result_text_widget, df, result_dict))
    search_button.pack(padx=10, pady=5)

    return search_entry, suggestion_listbox


def create_gpu_comparison_tab(parent):

    main_frame = ctk.CTkFrame(parent)
    main_frame.pack(fill="both", expand=True)

    left_frame = ctk.CTkFrame(main_frame, width=350, height=400)
    left_frame.pack(side="left", fill="both", expand=True, padx=10, pady=10)

    center_frame = ctk.CTkFrame(main_frame, width=350, height=400)
    center_frame.pack(side="left", fill="both", expand=True, padx=10, pady=10)

    right_frame = ctk.CTkFrame(main_frame, width=350, height=400)
    right_frame.pack(side="right", fill="both", expand=True, padx=10, pady=10)

    left_result_text_widget = tk.Text(left_frame, height=10, width=50, wrap="none")
    left_result_text_widget.pack(padx=10, pady=10)
    create_autocomplete_searchbox(
        left_frame, "Search GPU (Left):", df, left_result_text_widget, leftVals
    )

    right_result_text_widget = tk.Text(right_frame, height=10, width=50, wrap="none")
    right_result_text_widget.pack(padx=10, pady=10)
    create_autocomplete_searchbox(
        right_frame, "Search GPU (Right):", df, right_result_text_widget, rightVals
    )


    center_result_text_widget = tk.Text(center_frame, height=10, width=50, wrap="none")
    center_result_text_widget.pack(padx=10, pady=10)


    def on_compare_button_click():
        create_difference_table(
            leftVals, rightVals, center_result_text_widget
        )

    compare_button = ctk.CTkButton(center_frame, text="Compare", command=on_compare_button_click)
    compare_button.pack(padx=10, pady=10)

    return main_frame
