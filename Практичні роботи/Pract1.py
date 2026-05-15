import tkinter as tk
from tkinter import messagebox
from datetime import datetime

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


# Функція для обчислення віку
def calculate_age():
    try:
        birth_year = int(entry_birth_year.get())

        current_year = datetime.now().year

        age = current_year - birth_year

        if birth_year > current_year:
            messagebox.showerror(
                "Помилка",
                "Рік народження не може бути більшим за поточний!"
            )
        else:
            age_result_label.config(
                text=f"Ваш вік: {age} років"
            )

    except ValueError:
        messagebox.showerror(
            "Помилка",
            "Будь ласка, введіть коректний рік!"
        )


window = tk.Tk()
window.title("Практичне заняття №1")
window.geometry("400x750")


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


# Рівень 3


level3_label = tk.Label(
    window,
    text="Рівень 3",
    font=("Arial", 16, "bold")
)
level3_label.pack(pady=10)

task3_label = tk.Label(
    window,
    text="Обчислення віку користувача"
)
task3_label.pack(pady=5)

birth_year_label = tk.Label(
    window,
    text="Введіть рік народження:"
)
birth_year_label.pack()

entry_birth_year = tk.Entry(window)
entry_birth_year.pack(pady=5)

age_button = tk.Button(
    window,
    text="Обчислити вік",
    command=calculate_age
)
age_button.pack(pady=10)

age_result_label = tk.Label(
    window,
    text="Ваш вік:",
    font=("Arial", 12)
)
age_result_label.pack(pady=10)


window.mainloop()