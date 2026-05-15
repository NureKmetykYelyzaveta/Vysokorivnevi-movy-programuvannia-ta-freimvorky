package com.example.coursecatalogapp

import android.content.Context
import android.content.SharedPreferences

data class User(
    val name: String,
    val email: String,
    val password: String
)

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val teacher: String,
    val duration: String,
    var status: String = "Не розпочато",
    var rating: Float = 0f,
    var isEnrolled: Boolean = false
)

data class Review(
    val courseId: Int,
    val userName: String,
    val text: String,
    val rating: Int
)

data class LearningProgram(
    val id: Int,
    val title: String,
    val description: String,
    val courseIds: List<Int>
)

object DataRepository {

    private lateinit var prefs: SharedPreferences

    val users = mutableListOf<User>()

    val courses = mutableListOf(
        Course(
            id = 1,
            title = "Основи Kotlin",
            description = "Базовий курс для вивчення мови Kotlin з нуля.",
            teacher = "Іван Петренко",
            duration = "4 тижні",
            rating = 4.5f
        ),
        Course(
            id = 2,
            title = "Android-розробка",
            description = "Створення мобільних застосунків для Android.",
            teacher = "Олена Коваль",
            duration = "6 тижнів",
            rating = 4.8f
        ),
        Course(
            id = 3,
            title = "UI/UX дизайн",
            description = "Основи створення зручних інтерфейсів.",
            teacher = "Марина Сидоренко",
            duration = "3 тижні",
            rating = 4.2f
        ),
        Course(
            id = 4,
            title = "Основи баз даних",
            description = "Вивчення SQL, таблиць, зв’язків та запитів.",
            teacher = "Андрій Мельник",
            duration = "5 тижнів",
            rating = 4.6f
        ),
        Course(
            id = 5,
            title = "Web Frontend",
            description = "HTML, CSS, JavaScript та основи React.",
            teacher = "Наталія Бондар",
            duration = "7 тижнів",
            rating = 4.7f
        )
    )

    val reviews = mutableListOf(
        Review(1, "Анна", "Дуже зрозумілий курс для початківців.", 5),
        Review(2, "Максим", "Корисний курс, багато практики.", 5)
    )

    val programs = mutableListOf(
        LearningProgram(
            id = 1,
            title = "Android Developer",
            description = "Навчальна програма для підготовки Android-розробника.",
            courseIds = listOf(1, 2, 4)
        ),
        LearningProgram(
            id = 2,
            title = "Frontend Developer",
            description = "Програма для вивчення веб-розробки.",
            courseIds = listOf(3, 5)
        )
    )

    fun init(context: Context) {
        prefs = context.getSharedPreferences("course_catalog_prefs", Context.MODE_PRIVATE)

        if (users.isEmpty()) {
            users.add(User("Тестовий користувач", "test@gmail.com", "123456"))
        }

        loadUsers()
        loadCoursesState()
    }

    fun getCurrentUserEmail(): String? {
        return prefs.getString("current_user_email", null)
    }

    fun getCurrentUser(): User? {
        val email = getCurrentUserEmail()
        return users.find { it.email == email }
    }

    fun login(email: String, password: String): Boolean {
        val user = users.find { it.email == email && it.password == password }

        return if (user != null) {
            prefs.edit().putString("current_user_email", email).apply()
            true
        } else {
            false
        }
    }

    fun logout() {
        prefs.edit().remove("current_user_email").apply()
    }

    fun register(name: String, email: String, password: String): Boolean {
        val exists = users.any { it.email == email }

        if (exists) {
            return false
        }

        users.add(User(name, email, password))
        saveUsers()

        return true
    }

    fun getCourseById(id: Int): Course? {
        return courses.find { it.id == id }
    }

    fun enrollCourse(courseId: Int) {
        val course = getCourseById(courseId)

        if (course != null) {
            course.isEnrolled = true
            course.status = "Не розпочато"
            saveCoursesState()
        }
    }

    fun updateCourseStatus(courseId: Int) {
        val course = getCourseById(courseId)

        if (course != null) {
            course.status = when (course.status) {
                "Не розпочато" -> "В процесі"
                "В процесі" -> "Завершено"
                else -> "Не розпочато"
            }

            saveCoursesState()
        }
    }

    fun getMyCourses(): List<Course> {
        return courses.filter { it.isEnrolled }
    }

    fun addReview(courseId: Int, userName: String, text: String, rating: Int) {
        reviews.add(
            Review(
                courseId = courseId,
                userName = userName,
                text = text,
                rating = rating
            )
        )

        val courseReviews = reviews.filter { it.courseId == courseId }

        val averageRating = courseReviews.map { it.rating }.average().toFloat()
        getCourseById(courseId)?.rating = averageRating
    }

    fun getReviewsForCourse(courseId: Int): List<Review> {
        return reviews.filter { it.courseId == courseId }
    }

    fun getProgramProgress(program: LearningProgram): Int {
        val programCourses = courses.filter { program.courseIds.contains(it.id) }

        if (programCourses.isEmpty()) {
            return 0
        }

        val completedCount = programCourses.count { it.status == "Завершено" }

        return completedCount * 100 / programCourses.size
    }

    private fun saveUsers() {
        val usersString = users.joinToString(";") {
            "${it.name}|${it.email}|${it.password}"
        }

        prefs.edit().putString("users", usersString).apply()
    }

    private fun loadUsers() {
        val usersString = prefs.getString("users", null) ?: return

        users.clear()

        usersString.split(";").forEach { item ->
            val parts = item.split("|")

            if (parts.size == 3) {
                users.add(
                    User(
                        name = parts[0],
                        email = parts[1],
                        password = parts[2]
                    )
                )
            }
        }

        if (users.isEmpty()) {
            users.add(User("Тестовий користувач", "test@gmail.com", "123456"))
        }
    }

    private fun saveCoursesState() {
        val stateString = courses.joinToString(";") {
            "${it.id}|${it.status}|${it.isEnrolled}"
        }

        prefs.edit().putString("courses_state", stateString).apply()
    }

    private fun loadCoursesState() {
        val stateString = prefs.getString("courses_state", null) ?: return

        stateString.split(";").forEach { item ->
            val parts = item.split("|")

            if (parts.size == 3) {
                val id = parts[0].toIntOrNull()
                val status = parts[1]
                val isEnrolled = parts[2].toBoolean()

                val course = courses.find { it.id == id }

                if (course != null) {
                    course.status = status
                    course.isEnrolled = isEnrolled
                }
            }
        }
    }
}

