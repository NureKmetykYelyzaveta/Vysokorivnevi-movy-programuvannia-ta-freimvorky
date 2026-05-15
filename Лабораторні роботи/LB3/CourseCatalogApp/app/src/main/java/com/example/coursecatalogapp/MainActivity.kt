package com.example.coursecatalogapp

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var contentLayout: LinearLayout
    private var showMyCoursesNow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataRepository.init(this)

        if (DataRepository.getCurrentUserEmail() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        renderScreen(false)
    }

    override fun onResume() {
        super.onResume()
        DataRepository.init(this)

        if (::contentLayout.isInitialized) {
            showCourses(showMyCoursesNow)
        }
    }

    private fun renderScreen(showOnlyMyCourses: Boolean) {
        showMyCoursesNow = showOnlyMyCourses

        val scrollView = ScrollView(this)
        scrollView.setBackgroundColor(UiHelper.COLOR_BACKGROUND)

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setPadding(
            UiHelper.dp(this, 20),
            UiHelper.dp(this, 28),
            UiHelper.dp(this, 20),
            UiHelper.dp(this, 24)
        )

        val user = DataRepository.getCurrentUser()

        val title = UiHelper.title(this, "Каталог курсів")
        val subtitle = UiHelper.subtitle(
            this,
            "Вітаємо, ${user?.name ?: "користувачу"}! Обирайте курси, записуйтеся та відстежуйте прогрес."
        )

        val profileCard = UiHelper.card(this)

        val profileTitle = UiHelper.text(this, "Профіль користувача", 17f)
        profileTitle.typeface = Typeface.DEFAULT_BOLD

        val profileInfo = UiHelper.mutedText(
            this,
            "Ім'я: ${user?.name ?: "Невідомо"}\nEmail: ${user?.email ?: "-"}",
            15f
        )

        profileCard.addView(profileTitle)
        profileCard.addView(UiHelper.spacer(this, 6))
        profileCard.addView(profileInfo)

        val allCoursesButton = UiHelper.primaryButton(this, "Усі курси")
        val myCoursesButton = UiHelper.secondaryButton(this, "Мої курси")
        val programsButton = UiHelper.secondaryButton(this, "Навчальні програми")
        val logoutButton = UiHelper.dangerButton(this, "Вийти")

        contentLayout = LinearLayout(this)
        contentLayout.orientation = LinearLayout.VERTICAL

        root.addView(title)
        root.addView(subtitle)
        root.addView(profileCard)
        root.addView(allCoursesButton)
        root.addView(UiHelper.spacer(this, 10))
        root.addView(myCoursesButton)
        root.addView(UiHelper.spacer(this, 10))
        root.addView(programsButton)
        root.addView(UiHelper.spacer(this, 10))
        root.addView(logoutButton)
        root.addView(UiHelper.spacer(this, 18))
        root.addView(contentLayout)

        scrollView.addView(root)
        setContentView(scrollView)

        allCoursesButton.setOnClickListener {
            showCourses(false)
        }

        myCoursesButton.setOnClickListener {
            showCourses(true)
        }

        programsButton.setOnClickListener {
            startActivity(Intent(this, ProgramsActivity::class.java))
        }

        logoutButton.setOnClickListener {
            DataRepository.logout()
            Toast.makeText(this, "Ви вийшли з акаунта", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        showCourses(showOnlyMyCourses)
    }

    private fun showCourses(showOnlyMyCourses: Boolean) {
        showMyCoursesNow = showOnlyMyCourses
        contentLayout.removeAllViews()

        val courses = if (showOnlyMyCourses) {
            DataRepository.getMyCourses()
        } else {
            DataRepository.courses
        }

        val sectionTitle = UiHelper.sectionTitle(
            this,
            if (showOnlyMyCourses) "Мої курси" else "Усі доступні курси"
        )

        contentLayout.addView(sectionTitle)

        if (courses.isEmpty()) {
            val emptyCard = UiHelper.card(this)
            emptyCard.addView(
                UiHelper.mutedText(
                    this,
                    "Список порожній. Запишіться на курс у розділі «Усі курси».",
                    16f
                )
            )
            contentLayout.addView(emptyCard)
            return
        }

        for (course in courses) {
            val card = UiHelper.card(this)

            val courseTitle = UiHelper.text(this, course.title, 20f)
            courseTitle.typeface = Typeface.DEFAULT_BOLD

            val description = UiHelper.mutedText(this, course.description, 15f)

            val statusBadge = UiHelper.badge(
                this,
                course.status,
                UiHelper.statusColor(course.status)
            )

            val info = UiHelper.mutedText(
                this,
                "Викладач: ${course.teacher}\nТривалість: ${course.duration}\nРейтинг: ${String.format("%.1f", course.rating)} / 5",
                15f
            )

            val detailsButton = UiHelper.primaryButton(this, "Детальніше")
            val enrollButton = UiHelper.secondaryButton(
                this,
                if (course.isEnrolled) "Вже записано" else "Записатися"
            )
            val statusButton = UiHelper.secondaryButton(this, "Змінити статус")

            card.addView(courseTitle)
            card.addView(UiHelper.spacer(this, 8))
            card.addView(description)
            card.addView(UiHelper.spacer(this, 12))
            card.addView(statusBadge)
            card.addView(UiHelper.spacer(this, 12))
            card.addView(info)
            card.addView(UiHelper.spacer(this, 14))
            card.addView(detailsButton)
            card.addView(UiHelper.spacer(this, 8))
            card.addView(enrollButton)

            if (course.isEnrolled) {
                card.addView(UiHelper.spacer(this, 8))
                card.addView(statusButton)
            }

            contentLayout.addView(card)

            detailsButton.setOnClickListener {
                val intent = Intent(this, CourseDetailsActivity::class.java)
                intent.putExtra("course_id", course.id)
                startActivity(intent)
            }

            enrollButton.setOnClickListener {
                if (!course.isEnrolled) {
                    DataRepository.enrollCourse(course.id)
                    Toast.makeText(this, "Ви записалися на курс", Toast.LENGTH_SHORT).show()
                    showCourses(showOnlyMyCourses)
                } else {
                    Toast.makeText(this, "Ви вже записані на цей курс", Toast.LENGTH_SHORT).show()
                }
            }

            statusButton.setOnClickListener {
                DataRepository.updateCourseStatus(course.id)
                Toast.makeText(this, "Статус оновлено", Toast.LENGTH_SHORT).show()
                showCourses(showOnlyMyCourses)
            }
        }
    }
}