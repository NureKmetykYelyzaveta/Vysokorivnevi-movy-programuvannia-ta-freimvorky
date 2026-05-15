package com.example.coursecatalogapp

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

object UiHelper {

    const val COLOR_BACKGROUND = 0xFFF6F3FF.toInt()
    const val COLOR_CARD = 0xFFFFFFFF.toInt()
    const val COLOR_PRIMARY = 0xFF7C3AED.toInt()
    const val COLOR_PRIMARY_DARK = 0xFF5B21B6.toInt()
    const val COLOR_TEXT = 0xFF1F2937.toInt()
    const val COLOR_MUTED = 0xFF6B7280.toInt()
    const val COLOR_SUCCESS = 0xFF16A34A.toInt()
    const val COLOR_WARNING = 0xFFF59E0B.toInt()
    const val COLOR_DANGER = 0xFFDC2626.toInt()

    fun dp(context: Context, value: Int): Int {
        return (value * context.resources.displayMetrics.density).toInt()
    }

    fun roundedBackground(
        color: Int,
        radius: Float = 24f,
        strokeColor: Int? = null,
        strokeWidth: Int = 1
    ): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.setColor(color)
        drawable.cornerRadius = radius

        if (strokeColor != null) {
            drawable.setStroke(strokeWidth, strokeColor)
        }

        return drawable
    }

    fun title(context: Context, text: String): TextView {
        return TextView(context).apply {
            this.text = text
            textSize = 28f
            setTextColor(COLOR_TEXT)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 0, 0, dp(context, 8))
        }
    }

    fun subtitle(context: Context, text: String): TextView {
        return TextView(context).apply {
            this.text = text
            textSize = 16f
            setTextColor(COLOR_MUTED)
            setPadding(0, 0, 0, dp(context, 18))
        }
    }

    fun sectionTitle(context: Context, text: String): TextView {
        return TextView(context).apply {
            this.text = text
            textSize = 22f
            setTextColor(COLOR_TEXT)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, dp(context, 12), 0, dp(context, 12))
        }
    }

    fun text(context: Context, text: String, size: Float = 15f): TextView {
        return TextView(context).apply {
            this.text = text
            textSize = size
            setTextColor(COLOR_TEXT)
            setLineSpacing(4f, 1.1f)
        }
    }

    fun mutedText(context: Context, text: String, size: Float = 14f): TextView {
        return TextView(context).apply {
            this.text = text
            textSize = size
            setTextColor(COLOR_MUTED)
            setLineSpacing(4f, 1.1f)
        }
    }

    fun primaryButton(context: Context, text: String): Button {
        return Button(context).apply {
            this.text = text
            textSize = 15f
            setTextColor(Color.WHITE)
            background = roundedBackground(COLOR_PRIMARY, dp(context, 14).toFloat())
            setPadding(dp(context, 12), dp(context, 10), dp(context, 12), dp(context, 10))
            isAllCaps = false
        }
    }

    fun secondaryButton(context: Context, text: String): Button {
        return Button(context).apply {
            this.text = text
            textSize = 15f
            setTextColor(COLOR_PRIMARY_DARK)
            background = roundedBackground(
                0xFFEDE9FE.toInt(),
                dp(context, 14).toFloat()
            )
            setPadding(dp(context, 12), dp(context, 10), dp(context, 12), dp(context, 10))
            isAllCaps = false
        }
    }

    fun dangerButton(context: Context, text: String): Button {
        return Button(context).apply {
            this.text = text
            textSize = 15f
            setTextColor(Color.WHITE)
            background = roundedBackground(COLOR_DANGER, dp(context, 14).toFloat())
            setPadding(dp(context, 12), dp(context, 10), dp(context, 12), dp(context, 10))
            isAllCaps = false
        }
    }

    fun card(context: Context): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(context, 18), dp(context, 18), dp(context, 18), dp(context, 18))
            background = roundedBackground(
                COLOR_CARD,
                dp(context, 22).toFloat(),
                0xFFE5E7EB.toInt(),
                1
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(0, 0, 0, dp(context, 14))
            layoutParams = params
        }
    }

    fun badge(context: Context, text: String, color: Int): TextView {
        return TextView(context).apply {
            this.text = text
            textSize = 13f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(context, 10), dp(context, 5), dp(context, 10), dp(context, 5))
            background = roundedBackground(color, dp(context, 20).toFloat())
        }
    }

    fun spacer(context: Context, height: Int = 10): View {
        return View(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(context, height)
            )
        }
    }

    fun statusColor(status: String): Int {
        return when (status) {
            "Завершено" -> COLOR_SUCCESS
            "В процесі" -> COLOR_WARNING
            else -> COLOR_MUTED
        }
    }
}