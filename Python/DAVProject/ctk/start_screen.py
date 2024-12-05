import customtkinter as ctk

# the start screen of the gui. stays open when other screens are opened
def start_screen(app, open_graph_screen, open_settings_screen, open_info_screen):
    app.geometry("500x400")
    app.title("Start Screen")

    # Title
    title_label = ctk.CTkLabel(app, text="Data Analysis App", font=("Arial", 20, "bold"))
    title_label.pack(pady=40)

    # Buttons
    start_button = ctk.CTkButton(app, text="Start", width=200, command=open_graph_screen)
    start_button.pack(pady=20)

    settings_button = ctk.CTkButton(app, text="Settings", width=200, command=open_settings_screen)
    settings_button.pack(pady=20)

    info_button = ctk.CTkButton(app, text="Info", width=200, command=open_info_screen)
    info_button.pack(pady=20)
