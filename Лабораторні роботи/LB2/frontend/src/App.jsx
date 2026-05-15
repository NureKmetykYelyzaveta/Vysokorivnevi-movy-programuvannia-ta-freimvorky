import { useEffect, useState } from "react";
import "./App.css";

function App() {

    const API = "http://localhost:5000";

    const [courses, setCourses] = useState([]);

    const [studentNames, setStudentNames] = useState({});
    const [reviewData, setReviewData] = useState({});

    const [isLogin, setIsLogin] = useState(true);

    const [authData, setAuthData] = useState({
        username: "",
        password: ""
    });

    const [user, setUser] = useState(null);

    const [programs, setPrograms] = useState([]);

    const [programTitle, setProgramTitle] = useState("");

    const [selectedCourses, setSelectedCourses] = useState([]);





    useEffect(() => {

        const savedUser = localStorage.getItem("user");

        if (savedUser) {

            const parsedUser = JSON.parse(savedUser);

            setUser(parsedUser);

            loadPrograms(parsedUser.id);
        }

        loadCourses();

    }, []);





    const loadCourses = async () => {

        try {

            const response = await fetch(`${API}/api/courses`);

            const data = await response.json();

            setCourses(data);

        } catch (error) {

            console.error(error);
        }
    };





    const loadPrograms = async (userId) => {

        try {

            const response = await fetch(
                `${API}/api/programs/${userId}`
            );

            const data = await response.json();

            setPrograms(data);

        } catch (error) {

            console.error(error);
        }
    };





    const register = async () => {

        try {

            const response = await fetch(`${API}/api/register`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(authData)
            });

            const data = await response.json();

            alert(data.message);

            if (response.ok) {
                setIsLogin(true);
            }

        } catch (error) {

            console.error(error);
        }
    };





    const login = async () => {

        try {

            const response = await fetch(`${API}/api/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(authData)
            });

            const data = await response.json();

            if (!response.ok) {

                alert(data.message);

                return;
            }

            localStorage.setItem(
                "user",
                JSON.stringify(data.user)
            );

            setUser(data.user);

            loadPrograms(data.user.id);

        } catch (error) {

            console.error(error);
        }
    };





    const logout = () => {

        localStorage.removeItem("user");

        setUser(null);

        setPrograms([]);
    };





    const handleStudentNameChange = (courseId, value) => {

        setStudentNames({
            ...studentNames,
            [courseId]: value
        });
    };





    const handleReviewChange = (
        courseId,
        field,
        value
    ) => {

        setReviewData({
            ...reviewData,
            [courseId]: {
                ...reviewData[courseId],
                [field]: value
            }
        });
    };





    const enrollToCourse = async (courseId) => {

        const studentName = studentNames[courseId];

        if (!studentName || studentName.trim() === "") {

            alert("Введіть ім'я");

            return;
        }

        try {

            const response = await fetch(
                `${API}/api/courses/${courseId}/enroll`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        studentName
                    })
                }
            );

            const data = await response.json();

            alert(data.message);

            loadCourses();

        } catch (error) {

            console.error(error);
        }
    };





    const addReview = async (courseId) => {

        const review = reviewData[courseId] || {};

        try {

            const response = await fetch(
                `${API}/api/courses/${courseId}/reviews`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        userName: review.userName,
                        rating: Number(review.rating),
                        text: review.text
                    })
                }
            );

            const data = await response.json();

            alert(data.message);

            loadCourses();

        } catch (error) {

            console.error(error);
        }
    };





    const createProgram = async () => {

        if (!programTitle) {

            alert("Введіть назву програми");

            return;
        }

        try {

            const response = await fetch(
                `${API}/api/programs`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        title: programTitle,
                        courses: selectedCourses,
                        userId: user.id
                    })
                }
            );

            const data = await response.json();

            alert(data.message);

            setProgramTitle("");

            setSelectedCourses([]);

            loadPrograms(user.id);

        } catch (error) {

            console.error(error);
        }
    };





    const updateProgress = async (
        programId,
        progress
    ) => {

        try {

            await fetch(
                `${API}/api/programs/${programId}`,
                {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        progress
                    })
                }
            );

            loadPrograms(user.id);

        } catch (error) {

            console.error(error);
        }
    };





    if (!user) {

        return (

            <div className="auth-page">

                <div className="auth-card">

                    <h1>
                        Course Platform
                    </h1>

                    <h2>
                        {
                            isLogin
                                ? "Авторизація"
                                : "Реєстрація"
                        }
                    </h2>

                    <input
                        type="text"
                        placeholder="Логін"
                        value={authData.username}
                        onChange={(event) =>
                            setAuthData({
                                ...authData,
                                username: event.target.value
                            })
                        }
                    />

                    <input
                        type="password"
                        placeholder="Пароль"
                        value={authData.password}
                        onChange={(event) =>
                            setAuthData({
                                ...authData,
                                password: event.target.value
                            })
                        }
                    />

                    {
                        isLogin ? (
                            <button onClick={login}>
                                Увійти
                            </button>
                        ) : (
                            <button onClick={register}>
                                Зареєструватися
                            </button>
                        )
                    }

                    <p
                        className="switch-text"
                        onClick={() =>
                            setIsLogin(!isLogin)
                        }
                    >
                        {
                            isLogin
                                ? "Створити акаунт"
                                : "У мене вже є акаунт"
                        }
                    </p>

                </div>

            </div>
        );
    }





    return (

        <div className="app">

            <header className="header">

                <div>

                    <h1>
                        Каталог навчальних курсів
                    </h1>

                    <p>
                        Node.js + Express + React
                    </p>

                </div>

                <div className="user-panel">

                    <span>
                        {user.username}
                    </span>

                    <button onClick={logout}>
                        Вийти
                    </button>

                </div>

            </header>





            <main className="main-layout">

                <div className="courses-container">

                    {
                        courses.map(course => (

                            <div
                                className="course-card"
                                key={course.id}
                            >

                                <h2>
                                    {course.title}
                                </h2>

                                <p className="description">
                                    {course.description}
                                </p>

                                <p>
                                    <strong>
                                        Викладач:
                                    </strong>{" "}
                                    {course.teacher}
                                </p>

                                <p>
                                    <strong>
                                        Тривалість:
                                    </strong>{" "}
                                    {course.duration}
                                </p>

                                <p>
                                    <strong>
                                        Студентів:
                                    </strong>{" "}
                                    {course.students.length}
                                </p>

                                <p>
                                    <strong>
                                        Рейтинг:
                                    </strong>{" "}
                                    {
                                        course.averageRating > 0
                                            ? `${course.averageRating}/5`
                                            : "Немає"
                                    }
                                </p>





                                <div className="enroll-block">

                                    <h3>
                                        Запис на курс
                                    </h3>

                                    <input
                                        type="text"
                                        placeholder="Ваше ім'я"
                                        value={
                                            studentNames[course.id] || ""
                                        }
                                        onChange={(event) =>
                                            handleStudentNameChange(
                                                course.id,
                                                event.target.value
                                            )
                                        }
                                    />

                                    <button
                                        onClick={() =>
                                            enrollToCourse(course.id)
                                        }
                                    >
                                        Записатися
                                    </button>

                                </div>





                                <div className="review-block">

                                    <h3>
                                        Відгук
                                    </h3>

                                    <input
                                        type="text"
                                        placeholder="Ваше ім'я"
                                        value={
                                            reviewData[course.id]?.userName || ""
                                        }
                                        onChange={(event) =>
                                            handleReviewChange(
                                                course.id,
                                                "userName",
                                                event.target.value
                                            )
                                        }
                                    />

                                    <select
                                        value={
                                            reviewData[course.id]?.rating || ""
                                        }
                                        onChange={(event) =>
                                            handleReviewChange(
                                                course.id,
                                                "rating",
                                                event.target.value
                                            )
                                        }
                                    >

                                        <option value="">
                                            Оцінка
                                        </option>

                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>

                                    </select>

                                    <textarea
                                        placeholder="Текст відгуку"
                                        value={
                                            reviewData[course.id]?.text || ""
                                        }
                                        onChange={(event) =>
                                            handleReviewChange(
                                                course.id,
                                                "text",
                                                event.target.value
                                            )
                                        }
                                    />

                                    <button
                                        onClick={() =>
                                            addReview(course.id)
                                        }
                                    >
                                        Додати
                                    </button>

                                </div>





                                <div className="reviews-list">

                                    <h3>
                                        Відгуки
                                    </h3>

                                    {
                                        course.reviews.length > 0 ? (

                                            course.reviews.map(
                                                (review, index) => (

                                                    <div
                                                        className="review"
                                                        key={index}
                                                    >

                                                        <p>
                                                            <strong>
                                                                {review.userName}
                                                            </strong>{" "}
                                                            — {review.rating}/5
                                                        </p>

                                                        <p>
                                                            {review.text}
                                                        </p>

                                                    </div>
                                                )
                                            )

                                        ) : (

                                            <p className="empty">
                                                Відгуків ще немає
                                            </p>
                                        )
                                    }

                                </div>

                            </div>
                        ))
                    }

                </div>





                <aside className="programs-panel">

                    <h2>
                        Навчальні програми
                    </h2>

                    <input
                        type="text"
                        placeholder="Назва програми"
                        value={programTitle}
                        onChange={(event) =>
                            setProgramTitle(event.target.value)
                        }
                    />

                    <select
                        onChange={(event) =>
                            setSelectedCourses([
                                ...selectedCourses,
                                event.target.value
                            ])
                        }
                    >

                        <option>
                            Оберіть курс
                        </option>

                        {
                            courses.map(course => (

                                <option
                                    key={course.id}
                                    value={course.title}
                                >
                                    {course.title}
                                </option>
                            ))
                        }

                    </select>

                    <button onClick={createProgram}>
                        Створити програму
                    </button>





                    <div className="programs-list">

                        {
                            programs.map(program => (

                                <div
                                    className="program-card"
                                    key={program.id}
                                >

                                    <h3>
                                        {program.title}
                                    </h3>

                                    <p>
                                        <strong>
                                            Курси:
                                        </strong>
                                    </p>

                                    <ul>

                                        {
                                            program.courses.map(
                                                (course, index) => (

                                                    <li key={index}>
                                                        {course}
                                                    </li>
                                                )
                                            )
                                        }

                                    </ul>

                                    <p>
                                        <strong>
                                            Прогрес:
                                        </strong>{" "}
                                        {program.progress}%
                                    </p>

                                    <input
                                        type="range"
                                        min="0"
                                        max="100"
                                        value={program.progress}
                                        onChange={(event) =>
                                            updateProgress(
                                                program.id,
                                                Number(event.target.value)
                                            )
                                        }
                                    />

                                </div>
                            ))
                        }

                    </div>

                </aside>

            </main>

        </div>
    );
}

export default App;