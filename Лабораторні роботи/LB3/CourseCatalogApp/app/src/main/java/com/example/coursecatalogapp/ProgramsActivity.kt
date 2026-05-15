package com.example.coursecatalogapp

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView

class ProgramsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataRepository.init(this)

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

        val title = UiHelper.title(this, "Навчальні програми")
        val subtitle = UiHelper.subtitle(
            this,
            "Тут відображаються програми навчання та прогрес проходження курсів."
        )

        root.addView(title)
        root.addView(subtitle)

        for (program in DataRepository.programs) {
            val card = UiHelper.card(this)

            val programTitle = UiHelper.text(this, program.title, 21f)
            programTitle.typeface = Typeface.DEFAULT_BOLD

            val description = UiHelper.mutedText(this, program.description, 15f)

            val progress = DataRepository.getProgramProgress(program)

            val progressText = UiHelper.text(this, "Прогрес: $progress%", 16f)
            progressText.typeface = Typeface.DEFAULT_BOLD

            val progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal)
            progressBar.max = 100
            progressBar.progress = progress

            val coursesTitle = UiHelper.text(this, "Курси програми:", 16f)
            coursesTitle.typeface = Typeface.DEFAULT_BOLD

            card.addView(programTitle)
            card.addView(UiHelper.spacer(this, 8))
            card.addView(description)
            card.addView(UiHelper.spacer(this, 14))
            card.addView(progressText)
            card.addView(UiHelper.spacer(this, 8))
            card.addView(progressBar)
            card.addView(UiHelper.spacer(this, 14))
            card.addView(coursesTitle)

            val programCourses = DataRepository.courses.filter {
                program.courseIds.contains(it.id)
            }

            for (course in programCourses) {
                val courseLine = UiHelper.mutedText(
                    this,
                    "• ${course.title} — ${course.status}",
                    15f
                )

                card.addView(UiHelper.spacer(this, 6))
                card.addView(courseLine)
            }

            root.addView(card)
        }

        val backButton = UiHelper.secondaryButton(this, "Назад")
        root.addView(backButton)

        scrollView.addView(root)
        setContentView(scrollView)

        backButton.setOnClickListener {
            finish()
        }
    }
}