import tkinter as tk
from tkinter import messagebox

# Функція для показу чисел від 1 до 10
def show_numbers():
    numbers_text = ""

    for i in range(1, 11):
        numbers_text += str(i) + "\n"

    numbers_label.config(text=numbers_text)

# Функція для обчислення середнього значення
def calculate_average():
    try:
        number1 = float(entry_number1.get())
        number2 = float(entry_number2.get())
        number3 = float(entry_number3.get())

        average = (number1 + number2 + number3) / 3

        result_label.config(
            text=f"Середнє значення: {average}"
        )

    except ValueError:
        messagebox.showerror(
            "Помилка",
            "Будь ласка, введіть коректні числа!"
        )


window = tk.Tk()
window.title("Практичне заняття №1")
window.geometry("400x550")


# Рівень 1


level1_label = tk.Label(
    window,
    text="Рівень 1",
    font=("Arial", 16, "bold")
)
level1_label.pack(pady=10)

task1_label = tk.Label(
    window,
    text="Виведення чисел від 1 до 10"
)
task1_label.pack()

show_button = tk.Button(
    window,
    text="Показати числа",
    command=show_numbers
)
show_button.pack(pady=10)

numbers_label = tk.Label(
    window,
    text="",
    font=("Arial", 12)
)
numbers_label.pack()


# Рівень 2


level2_label = tk.Label(
    window,
    text="Рівень 2",
    font=("Arial", 16, "bold")
)
level2_label.pack(pady=10)

task2_label = tk.Label(
    window,
    text="Знаходження середнього значення трьох чисел"
)
task2_label.pack(pady=5)

label_number1 = tk.Label(window, text="Введіть перше число:")
label_number1.pack()

entry_number1 = tk.Entry(window)
entry_number1.pack(pady=5)

label_number2 = tk.Label(window, text="Введіть друге число:")
label_number2.pack()

entry_number2 = tk.Entry(window)
entry_number2.pack(pady=5)

label_number3 = tk.Label(window, text="Введіть третє число:")
label_number3.pack()

entry_number3 = tk.Entry(window)
entry_number3.pack(pady=5)

calculate_button = tk.Button(
    window,
    text="Обчислити середнє",
    command=calculate_average
)
calculate_button.pack(pady=10)

result_label = tk.Label(
    window,
    text="Середнє значення:",
    font=("Arial", 12)
)
result_label.pack(pady=10)


window.mainloop()