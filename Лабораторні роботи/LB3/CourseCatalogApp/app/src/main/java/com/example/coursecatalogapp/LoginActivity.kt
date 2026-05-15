package com.example.coursecatalogapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataRepository.init(this)

        if (DataRepository.getCurrentUserEmail() != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

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

        val title = UiHelper.title(this, "Каталог курсів")
        title.gravity = Gravity.CENTER

        val subtitle = UiHelper.subtitle(this, "Увійдіть у систему, щоб переглядати курси, записуватися на них і відстежувати прогрес.")
        subtitle.gravity = Gravity.CENTER

        val emailInput = EditText(this)
        emailInput.hint = "Email"
        emailInput.setText("test@gmail.com")
        emailInput.setPadding(25, 15, 25, 15)
        emailInput.background = UiHelper.roundedBackground(0xFFF9FAFB.toInt(), UiHelper.dp(this, 14).toFloat(), 0xFFD1D5DB.toInt())

        val passwordInput = EditText(this)
        passwordInput.hint = "Пароль"
        passwordInput.setText("123456")
        passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        passwordInput.setPadding(25, 15, 25, 15)
        passwordInput.background = UiHelper.roundedBackground(0xFFF9FAFB.toInt(), UiHelper.dp(this, 14).toFloat(), 0xFFD1D5DB.toInt())

        val loginButton = UiHelper.primaryButton(this, "Увійти")
        val registerButton = UiHelper.secondaryButton(this, "Створити акаунт")

        card.addView(title)
        card.addView(subtitle)
        card.addView(emailInput)
        card.addView(UiHelper.spacer(this, 12))
        card.addView(passwordInput)
        card.addView(UiHelper.spacer(this, 18))
        card.addView(loginButton)
        card.addView(UiHelper.spacer(this, 10))
        card.addView(registerButton)

        root.addView(card)
        scrollView.addView(root)
        setContentView(scrollView)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заповніть усі поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isSuccess = DataRepository.login(email, password)

            if (isSuccess) {
                Toast.makeText(this, "Вхід виконано успішно", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Невірний email або пароль", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}