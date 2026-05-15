package com.example.coursecatalogapp

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.ScrollView
import android.widget.Toast

class CourseDetailsActivity : Activity() {

    private var courseId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataRepository.init(this)

        courseId = intent.getIntExtra("course_id", -1)

        renderScreen()
    }

    private fun renderScreen() {
        val course = DataRepository.getCourseById(courseId)

        if (course == null) {
            Toast.makeText(this, "Курс не знайдено", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

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

        val title = UiHelper.title(this, course.title)
        val subtitle = UiHelper.subtitle(this, "Детальна інформація про курс, запис, статус проходження та відгуки.")

        val infoCard = UiHelper.card(this)

        val description = UiHelper.text(this, course.description, 16f)

        val statusBadge = UiHelper.badge(
            this,
            course.status,
            UiHelper.statusColor(course.status)
        )

        val info = UiHelper.mutedText(
            this,
            "Викладач: ${course.teacher}\nТривалість: ${course.duration}\nСередній рейтинг: ${String.format("%.1f", course.rating)} / 5",
            15f
        )

        val enrollButton = UiHelper.primaryButton(
            this,
            if (course.isEnrolled) "Ви вже записані" else "Записатися на курс"
        )

        val statusButton = UiHelper.secondaryButton(this, "Змінити статус проходження")

        infoCard.addView(description)
        infoCard.addView(UiHelper.spacer(this, 12))
        infoCard.addView(statusBadge)
        infoCard.addView(UiHelper.spacer(this, 12))
        infoCard.addView(info)
        infoCard.addView(UiHelper.spacer(this, 16))
        infoCard.addView(enrollButton)

        if (course.isEnrolled) {
            infoCard.addView(UiHelper.spacer(this, 8))
            infoCard.addView(statusButton)
        }

        val reviewCard = UiHelper.card(this)

        val reviewsTitle = UiHelper.text(this, "Додати відгук", 20f)
        reviewsTitle.typeface = Typeface.DEFAULT_BOLD

        val reviewInput = EditText(this)
        reviewInput.hint = "Напишіть свій відгук"
        reviewInput.minLines = 3
        reviewInput.setPadding(25, 15, 25, 15)
        reviewInput.background = UiHelper.roundedBackground(
            0xFFF9FAFB.toInt(),
            UiHelper.dp(this, 14).toFloat(),
            0xFFD1D5DB.toInt()
        )

        val ratingBar = RatingBar(this)
        ratingBar.numStars = 5
        ratingBar.stepSize = 1f
        ratingBar.rating = 5f

        val addReviewButton = UiHelper.primaryButton(this, "Додати відгук")

        reviewCard.addView(reviewsTitle)
        reviewCard.addView(UiHelper.spacer(this, 12))
        reviewCard.addView(reviewInput)
        reviewCard.addView(UiHelper.spacer(this, 10))
        reviewCard.addView(ratingBar)
        reviewCard.addView(UiHelper.spacer(this, 12))
        reviewCard.addView(addReviewButton)

        val reviewsListTitle = UiHelper.sectionTitle(this, "Відгуки користувачів")

        root.addView(title)
        root.addView(subtitle)
        root.addView(infoCard)
        root.addView(reviewCard)
        root.addView(reviewsListTitle)

        val reviews = DataRepository.getReviewsForCourse(course.id)

        if (reviews.isEmpty()) {
            val emptyCard = UiHelper.card(this)
            emptyCard.addView(UiHelper.mutedText(this, "Відгуків поки немає.", 16f))
            root.addView(emptyCard)
        } else {
            for (review in reviews) {
                val reviewView = UiHelper.card(this)

                val user = UiHelper.text(this, review.userName, 16f)
                user.typeface = Typeface.DEFAULT_BOLD

                val rating = UiHelper.badge(this, "Оцінка: ${review.rating}/5", UiHelper.COLOR_PRIMARY)

                val text = UiHelper.mutedText(this, review.text, 15f)

                reviewView.addView(user)
                reviewView.addView(UiHelper.spacer(this, 8))
                reviewView.addView(rating)
                reviewView.addView(UiHelper.spacer(this, 8))
                reviewView.addView(text)

                root.addView(reviewView)
            }
        }

        val backButton = UiHelper.secondaryButton(this, "Назад")
        root.addView(backButton)

        scrollView.addView(root)
        setContentView(scrollView)

        enrollButton.setOnClickListener {
            if (!course.isEnrolled) {
                DataRepository.enrollCourse(course.id)
                Toast.makeText(this, "Ви записалися на курс", Toast.LENGTH_SHORT).show()
                renderScreen()
            } else {
                Toast.makeText(this, "Ви вже записані на цей курс", Toast.LENGTH_SHORT).show()
            }
        }

        statusButton.setOnClickListener {
            DataRepository.updateCourseStatus(course.id)
            Toast.makeText(this, "Статус оновлено", Toast.LENGTH_SHORT).show()
            renderScreen()
        }

        addReviewButton.setOnClickListener {
            val text = reviewInput.text.toString().trim()
            val rating = ratingBar.rating.toInt()

            if (text.isEmpty()) {
                Toast.makeText(this, "Введіть текст відгуку", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userName = DataRepository.getCurrentUser()?.name ?: "Користувач"

            DataRepository.addReview(
                courseId = course.id,
                userName = userName,
                text = text,
                rating = rating
            )

            Toast.makeText(this, "Відгук додано", Toast.LENGTH_SHORT).show()
            renderScreen()
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}