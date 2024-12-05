import customtkinter as ctk

# this screen is a simple proof of concept that allows the user to adjust the global font size
# via a scrollbar

def settings_screen():
    settings_window = ctk.CTk()
    settings_window.geometry("400x300")
    settings_window.title("Settings")

    # function to adjust global scaling
    def adjust_scaling(value):
        ctk.set_widget_scaling(float(value))

    # function to reset scaling to default
    def reset_scaling():
        font_slider.set(1.0)  # reset slider position
        adjust_scaling(1.0)   # Reset scaling to default

    label = ctk.CTkLabel(settings_window, text="Settings Window", font=("Arial", 16))
    label.pack(pady=20)

    # slider to adjust scaling
    slider_label = ctk.CTkLabel(settings_window, text="Adjust Font Scaling", font=("Arial", 14))
    slider_label.pack(pady=10)

    font_slider = ctk.CTkSlider(
        settings_window,
        from_=1.0,
        to=2.0,
        command=adjust_scaling,
        number_of_steps=15  # sets how many steps between 1x to 2x
    )
    font_slider.pack(pady=10)
    font_slider.set(1.0)  # Default scaling is 1x

    # Reset button
    reset_button = ctk.CTkButton(
        settings_window,
        text="Reset to Default",
        command=reset_scaling
    )
    reset_button.pack(pady=20)

    settings_window.mainloop()


