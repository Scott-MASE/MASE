import customtkinter as ctk
import pandas as pd
from ctk.start_screen import start_screen
from ctk.tab_driver import tab_driver
from ctk.settings_screen import settings_screen
from ctk.info_screen import info_screen

def tkcDriver():
    # starts the main app window and opens the start screen
    app = ctk.CTk()


    # Navigation functions
    def open_tab_screen():
        tab_driver()

    def open_settings_screen():
        settings_screen()

    def open_info_screen():
        info_screen()

    # load the start screen and pass the three start screens to it
    start_screen(app, open_tab_screen, open_settings_screen, open_info_screen)

    # Run the application
    app.mainloop()




