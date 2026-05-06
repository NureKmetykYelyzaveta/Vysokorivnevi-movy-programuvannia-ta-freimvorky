const express = require("express");
const cors = require("cors");

const app = express();
const PORT = 5000;

app.use(cors());
app.use(express.json());

let courses = [
    {
        id: 1,
        title: "Основи JavaScript",
        description: "Курс для початківців з основ JavaScript, змінних, функцій та масивів.",
        teacher: "Іван Петренко",
        duration: "4 тижні",
        students: [],
        reviews: [
            {
                userName: "Олена",
                rating: 5,
                text: "Дуже зрозумілий курс для початку навчання."
            }
        ]
    },
    {
        id: 2,
        title: "React для початківців",
        description: "Курс з основ React: компоненти, props, state та робота з API.",
        teacher: "Марія Коваленко",
        duration: "6 тижнів",
        students: [],
        reviews: [
            {
                userName: "Андрій",
                rating: 4,
                text: "Корисний курс, багато практики."
            }
        ]
    },
    {
        id: 3,
        title: "Node.js та Express",
        description: "Створення серверних застосунків на Node.js з використанням Express.",
        teacher: "Олександр Бондар",
        duration: "5 тижнів",
        students: [],
        reviews: []
    }
];

function calculateAverageRating(reviews) {
    if (reviews.length === 0) {
        return 0;
    }

    const sum = reviews.reduce((total, review) => total + Number(review.rating), 0);
    return Number((sum / reviews.length).toFixed(1));
}

app.get("/", (req, res) => {
    res.send("Сервер каталогу курсів працює");
});

app.get("/api/courses", (req, res) => {
    const coursesWithRating = courses.map(course => ({
        ...course,
        averageRating: calculateAverageRating(course.reviews)
    }));

    res.json(coursesWithRating);
});

app.post("/api/courses/:id/enroll", (req, res) => {
    const courseId = Number(req.params.id);
    const { studentName } = req.body;

    const course = courses.find(item => item.id === courseId);

    if (!course) {
        return res.status(404).json({ message: "Курс не знайдено" });
    }

    if (!studentName || studentName.trim() === "") {
        return res.status(400).json({ message: "Ім'я студента не може бути порожнім" });
    }

    course.students.push(studentName);

    res.json({
        message: "Ви успішно записались на курс",
        course
    });
});

app.post("/api/courses/:id/reviews", (req, res) => {
    const courseId = Number(req.params.id);
    const { userName, rating, text } = req.body;

    const course = courses.find(item => item.id === courseId);

    if (!course) {
        return res.status(404).json({ message: "Курс не знайдено" });
    }

    if (!userName || userName.trim() === "") {
        return res.status(400).json({ message: "Ім'я користувача не може бути порожнім" });
    }

    if (!rating || rating < 1 || rating > 5) {
        return res.status(400).json({ message: "Рейтинг має бути від 1 до 5" });
    }

    if (!text || text.trim() === "") {
        return res.status(400).json({ message: "Текст відгуку не може бути порожнім" });
    }

    const newReview = {
        userName,
        rating: Number(rating),
        text
    };

    course.reviews.push(newReview);

    res.json({
        message: "Відгук успішно додано",
        course
    });
});

app.listen(PORT, () => {
    console.log(`Сервер запущено на http://localhost:${PORT}`);
});