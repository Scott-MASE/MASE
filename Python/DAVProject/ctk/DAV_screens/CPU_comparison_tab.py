import customtkinter as ctk
import tkinter as tk  # Import tkinter for the Listbox
import pandas as pd
from functools import partial


leftVals = {}
rightVals = {}

# This tab is divided into three sections, left and right for CPUs and middle for comparison between the two cpus

#this function searches the CPU df for a cpu by its name, and creates a table containing all its info
def search_cpu(entry, result_text_widget, df, result_dict):
    cpu_name = entry.get()
    result = df[df["CPUname"].str.contains(cpu_name, case=False, na=False)]

    result_text_widget.delete("1.0", tk.END)

    if not result.empty:
        # calculate table heigth based on lenght
        num_rows = len(result.columns) + 2  # add 2 for header and separator
        buffer = 2  # add a buffer to make sure all fits
        new_height = num_rows + buffer
        result_text_widget.config(height=new_height)

        # create the results table header
        header = "{:<25} {:<23}\n".format("Column", "Value")
        separator = "-" * 50 + "\n"
        result_text_widget.insert(tk.END, header)
        result_text_widget.insert(tk.END, separator)

        #populate the results table
        for col in df.columns:
            print(type(result[col].values[0]))
            row = "{:<25} {:<23}\n".format(col, result[col].values[0])
            result_text_widget.insert(tk.END, row)

            # Append the column values to the appropriate dictionary
            result_dict[col] = result[col]
    # if the searched cpu isnt found
    else:
        result_text_widget.config(height=5)  # reset height for no results
        result_text_widget.insert(tk.END, "No matching CPU found.")

# this function creates the center table which takes in the values from left and right table and calculates the diff
def create_difference_table(leftVals, rightVals, result_text_widget):

    num_rows = len(leftVals) + 2
    buffer = 2
    new_height = num_rows + buffer
    result_text_widget.config(height=new_height)

    # Create a table for differences
    header = "{:<25} {:<10} {:<10} \n".format("Column", "Diff", "Percent Diff")
    separator = "-" * 50 + "\n"
    result_text_widget.delete("1.0", tk.END)
    result_text_widget.insert(tk.END, header)
    result_text_widget.insert(tk.END, separator)

    if leftVals != {} and rightVals != {}:
        # ignores the follwing cols, as their data cant be compared
        keys_to_skip = {"ID", "CPUname","Type","Release Date","Foundry","Vendor","testDate","socket","category"}

        for col in leftVals.keys():
            left_val = leftVals[col].values[0]
            right_val = rightVals[col].values[0]
            # Default to "N/A"
            diff = "N/A"
            percDiff = "N/A"
            #Skips the col
            if col in keys_to_skip:
                continue

            try:
                # ensures it only calculates valid numerical data
                if str(left_val).lower() not in {"nan", "none", ""} and str(right_val).lower() not in {"nan", "none",
                                                                                                       ""}:
                    diff = round(float(right_val) - float(left_val), 2)
                    percDiff = f"{round((float(right_val) - float(left_val)) / float(left_val) * 100, 2)}%"
            except (ValueError, TypeError):
                pass  # keep diff as "N/A" if conversion fails

            row = "{:<25} {:<10} {:<10}\n".format(col, diff, percDiff)
            result_text_widget.insert(tk.END, row)
    #comparison table only works when bot the left and right tables are populated
    else:
        result_text_widget.insert(tk.END, "No comparison available.")


# autocomplete functionality. when user starts typing, a table populates with all possible matches. if user clicks one,
# it is inserted into the search box. not case-sensitive
def autocomplete(entry, listbox, suggestions, result_label, event=None):
    typed_text = entry.get().lower()
    # clear the current listbox and add matching suggestions every letter typed
    listbox.delete(0, tk.END)

    # get all suggestions that start with the typed text
    matches = [cpu for cpu in suggestions if cpu.lower().startswith(typed_text)]

    for match in matches:
        listbox.insert(tk.END, match)

    # If the Listbox is empty, hide it
    if not matches:
        listbox.pack_forget()
    else:
        listbox.pack(padx=10, pady=5, fill="x")  # Show Listbox above the result label


# when user clicks cpu in listbox, it is automatically passed to the search_cpu func
def on_suggestion_click(entry, listbox, suggestions, result_label, event=None):
    df = pd.read_csv("Temp/F_CPU_Data.csv")
    # Get the selected item from the Listbox
    selected_item = listbox.get(listbox.curselection())
    # Set the selected item as the text in the search entry
    entry.delete(0, tk.END)
    entry.insert(0, selected_item)

    # hide the Listbox after selection
    listbox.pack_forget()

    # Trigger the search function after selection
    search_cpu(entry, result_label, df, result_label)

#
def create_autocomplete_searchbox(frame, label_text, df, result_text_widget, result_dict):
    search_label = ctk.CTkLabel(frame, text=label_text)
    search_label.pack(padx=10, pady=5)

    # search bar for cpus
    search_entry = ctk.CTkEntry(frame, width=300)
    search_entry.pack(padx=10, pady=5)

    # suggestion box
    suggestion_listbox = tk.Listbox(frame, height=10, width=40)
    suggestion_listbox.pack_forget()  # Initially hide the listbox

    # Whenever a new character is typed in the search box, update suggestion box
    search_entry.bind('<KeyRelease>',
                      partial(autocomplete, search_entry, suggestion_listbox, df["CPUname"].tolist(), result_text_widget))

    # bind the Listbox click event to update the search entry with the selected suggestion
    suggestion_listbox.bind('<ButtonRelease-1>',
                            partial(on_suggestion_click, search_entry, suggestion_listbox, df["CPUname"].tolist(),
                                    result_text_widget))

    search_button = ctk.CTkButton(frame, text="Search", command=partial(search_cpu, search_entry, result_text_widget, df, result_dict))
    search_button.pack(padx=10, pady=5)

    return search_entry, suggestion_listbox


def create_cpu_comparison_tab(parent):
    df = pd.read_csv("Temp/F_CPU_Data.csv")

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
        left_frame, "Search CPU (Left):", df, left_result_text_widget, leftVals
    )

    right_result_text_widget = tk.Text(right_frame, height=10, width=50, wrap="none")
    right_result_text_widget.pack(padx=10, pady=10)
    create_autocomplete_searchbox(
        right_frame, "Search CPU (Right):", df, right_result_text_widget, rightVals
    )

    center_result_text_widget = tk.Text(center_frame, height=10, width=50, wrap="none")
    center_result_text_widget.pack(padx=10, pady=10)

    # populates center table when pressed
    def on_compare_button_click():
        create_difference_table(
            leftVals, rightVals, center_result_text_widget
        )

    compare_button = ctk.CTkButton(center_frame, text="Compare", command=on_compare_button_click)
    compare_button.pack(padx=10, pady=10)

    return main_frame
