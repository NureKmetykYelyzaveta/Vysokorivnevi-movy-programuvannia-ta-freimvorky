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




let users = [];




let learningPrograms = [
    {
        id: 1,
        userId: 1,
        title: "Frontend Developer",
        courses: [
            "Основи JavaScript",
            "React для початківців"
        ],
        progress: 50
    }
];




function calculateAverageRating(reviews) {

    if (reviews.length === 0) {
        return 0;
    }

    const sum = reviews.reduce(
        (total, review) =>
            total + Number(review.rating),
        0
    );

    return Number(
        (sum / reviews.length).toFixed(1)
    );
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

    const course = courses.find(
        item => item.id === courseId
    );

    if (!course) {
        return res.status(404).json({
            message: "Курс не знайдено"
        });
    }

    if (!studentName || studentName.trim() === "") {
        return res.status(400).json({
            message: "Ім'я студента не може бути порожнім"
        });
    }

    course.students.push(studentName);

    res.json({
        message: "Ви успішно записались на курс",
        course
    });
});




app.post("/api/courses/:id/reviews", (req, res) => {

    const courseId = Number(req.params.id);

    const {
        userName,
        rating,
        text
    } = req.body;

    const course = courses.find(
        item => item.id === courseId
    );

    if (!course) {
        return res.status(404).json({
            message: "Курс не знайдено"
        });
    }

    if (!userName || userName.trim() === "") {
        return res.status(400).json({
            message: "Ім'я користувача не може бути порожнім"
        });
    }

    if (!rating || rating < 1 || rating > 5) {
        return res.status(400).json({
            message: "Рейтинг має бути від 1 до 5"
        });
    }

    if (!text || text.trim() === "") {
        return res.status(400).json({
            message: "Текст відгуку не може бути порожнім"
        });
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




app.post("/api/register", (req, res) => {

    const {
        username,
        password
    } = req.body;

    const existingUser = users.find(
        user => user.username === username
    );

    if (existingUser) {
        return res.status(400).json({
            message: "Користувач вже існує"
        });
    }

    if (!username || username.trim() === "") {
        return res.status(400).json({
            message: "Логін не може бути порожнім"
        });
    }

    if (!password || password.length < 4) {
        return res.status(400).json({
            message: "Пароль має містити мінімум 4 символи"
        });
    }

    const newUser = {
        id: Date.now(),
        username,
        password
    };

    users.push(newUser);

    res.json({
        message: "Реєстрація успішна",
        user: newUser
    });
});




app.post("/api/login", (req, res) => {

    const {
        username,
        password
    } = req.body;

    const user = users.find(
        item =>
            item.username === username &&
            item.password === password
    );

    if (!user) {
        return res.status(401).json({
            message: "Невірний логін або пароль"
        });
    }

    res.json({
        message: "Вхід успішний",
        user
    });
});




app.get("/api/programs/:userId", (req, res) => {

    const userId = Number(req.params.userId);

    const userPrograms = learningPrograms.filter(
        item => item.userId === userId
    );

    res.json(userPrograms);
});



app.post("/api/programs", (req, res) => {

    const {
    title,
    courses,
    userId
} = req.body;

    if (!title || title.trim() === "") {
        return res.status(400).json({
            message: "Назва програми не може бути порожньою"
        });
    }

    const newProgram = {
    id: Date.now(),
    userId,
    title,
    courses,
    progress: 0
};

    learningPrograms.push(newProgram);

    res.json({
        message: "Програму створено",
        program: newProgram
    });
});




app.put("/api/programs/:id", (req, res) => {

    const programId = Number(req.params.id);

    const { progress } = req.body;

    const program = learningPrograms.find(
        item => item.id === programId
    );

    if (!program) {
        return res.status(404).json({
            message: "Програму не знайдено"
        });
    }

    if (progress < 0 || progress > 100) {
        return res.status(400).json({
            message: "Прогрес має бути від 0 до 100"
        });
    }

    program.progress = progress;

    res.json({
        message: "Прогрес оновлено",
        program
    });
});




app.listen(PORT, () => {
    console.log(
        `Сервер запущено на http://localhost:${PORT}`
    );
});