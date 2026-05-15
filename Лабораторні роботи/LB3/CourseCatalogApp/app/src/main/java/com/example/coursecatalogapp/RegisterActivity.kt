package com.example.coursecatalogapp

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast

class RegisterActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataRepository.init(this)

        val scrollView = ScrollView(this)
        scrollView.setBackgroundColor(UiHelper.COLOR_BACKGROUND)

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.gravity = Gravity.CENTER
        root.setPadding(
            UiHelper.dp(this, 24),
            UiHelper.dp(this, 50),
            UiHelper.dp(this, 24),
            UiHelper.dp(this, 24)
        )

        val card = UiHelper.card(this)
        card.setPadding(
            UiHelper.dp(this, 24),
            UiHelper.dp(this, 28),
            UiHelper.dp(this, 24),
            UiHelper.dp(this, 28)
        )

        val title = UiHelper.title(this, "Реєстрація")
        title.gravity = Gravity.CENTER

        val subtitle = UiHelper.subtitle(this, "Створіть акаунт для роботи з каталогом курсів.")
        subtitle.gravity = Gravity.CENTER

        val nameInput = EditText(this)
        nameInput.hint = "Ім'я"
        nameInput.setPadding(25, 15, 25, 15)
        nameInput.background = UiHelper.roundedBackground(0xFFF9FAFB.toInt(), UiHelper.dp(this, 14).toFloat(), 0xFFD1D5DB.toInt())

        val emailInput = EditText(this)
        emailInput.hint = "Email"
        emailInput.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        emailInput.setPadding(25, 15, 25, 15)
        emailInput.background = UiHelper.roundedBackground(0xFFF9FAFB.toInt(), UiHelper.dp(this, 14).toFloat(), 0xFFD1D5DB.toInt())

        val passwordInput = EditText(this)
        passwordInput.hint = "Пароль"
        passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        passwordInput.setPadding(25, 15, 25, 15)
        passwordInput.background = UiHelper.roundedBackground(0xFFF9FAFB.toInt(), UiHelper.dp(this, 14).toFloat(), 0xFFD1D5DB.toInt())

        val registerButton = UiHelper.primaryButton(this, "Зареєструватися")
        val backButton = UiHelper.secondaryButton(this, "Назад")

        card.addView(title)
        card.addView(subtitle)
        card.addView(nameInput)
        card.addView(UiHelper.spacer(this, 12))
        card.addView(emailInput)
        card.addView(UiHelper.spacer(this, 12))
        card.addView(passwordInput)
        card.addView(UiHelper.spacer(this, 18))
        card.addView(registerButton)
        card.addView(UiHelper.spacer(this, 10))
        card.addView(backButton)

        root.addView(card)
        scrollView.addView(root)
        setContentView(scrollView)

        registerButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заповніть усі поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isCreated = DataRepository.register(name, email, password)

            if (isCreated) {
                Toast.makeText(this, "Акаунт створено", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Користувач з таким email вже існує", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}