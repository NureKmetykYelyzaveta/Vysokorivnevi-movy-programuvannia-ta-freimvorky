import { useEffect, useState } from "react";
import "./App.css";

function App() {
    const [courses, setCourses] = useState([]);
    const [studentNames, setStudentNames] = useState({});
    const [reviewData, setReviewData] = useState({});

    const loadCourses = async () => {
        try {
            const response = await fetch("http://localhost:5000/api/courses");
            const data = await response.json();
            setCourses(data);
        } catch (error) {
            console.error("Помилка завантаження курсів:", error);
        }
    };

    useEffect(() => {
        loadCourses();
    }, []);

    const handleStudentNameChange = (courseId, value) => {
        setStudentNames({
            ...studentNames,
            [courseId]: value
        });
    };

    const handleReviewChange = (courseId, field, value) => {
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
            alert("Введіть ім'я для запису на курс");
            return;
        }

        try {
            const response = await fetch(`http://localhost:5000/api/courses/${courseId}/enroll`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ studentName })
            });

            const data = await response.json();

            if (!response.ok) {
                alert(data.message);
                return;
            }

            alert(data.message);

            setStudentNames({
                ...studentNames,
                [courseId]: ""
            });

            loadCourses();
        } catch (error) {
            console.error("Помилка запису на курс:", error);
        }
    };

    const addReview = async (courseId) => {
        const review = reviewData[courseId] || {};

        if (!review.userName || !review.rating || !review.text) {
            alert("Заповніть усі поля відгуку");
            return;
        }

        try {
            const response = await fetch(`http://localhost:5000/api/courses/${courseId}/reviews`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    userName: review.userName,
                    rating: Number(review.rating),
                    text: review.text
                })
            });

            const data = await response.json();

            if (!response.ok) {
                alert(data.message);
                return;
            }

            alert(data.message);

            setReviewData({
                ...reviewData,
                [courseId]: {
                    userName: "",
                    rating: "",
                    text: ""
                }
            });

            loadCourses();
        } catch (error) {
            console.error("Помилка додавання відгуку:", error);
        }
    };

    return (
        <div className="app">
            <header className="header">
                <h1>Каталог навчальних курсів</h1>
                <p>Node.js + Express + React</p>
            </header>

            <main className="courses-container">
                {courses.map(course => (
                    <div className="course-card" key={course.id}>
                        <h2>{course.title}</h2>

                        <p className="description">{course.description}</p>

                        <p>
                            <strong>Викладач:</strong> {course.teacher}
                        </p>

                        <p>
                            <strong>Тривалість:</strong> {course.duration}
                        </p>

                        <p>
                            <strong>Кількість студентів:</strong> {course.students.length}
                        </p>

                        <p>
                            <strong>Середній рейтинг:</strong>{" "}
                            {course.averageRating > 0
                                ? `${course.averageRating} / 5`
                                : "Ще немає оцінок"}
                        </p>

                        <div className="enroll-block">
                            <h3>Запис на курс</h3>

                            <input
                                type="text"
                                placeholder="Ваше ім'я"
                                value={studentNames[course.id] || ""}
                                onChange={(event) =>
                                    handleStudentNameChange(course.id, event.target.value)
                                }
                            />

                            <button onClick={() => enrollToCourse(course.id)}>
                                Записатися
                            </button>
                        </div>

                        <div className="review-block">
                            <h3>Додати відгук</h3>

                            <input
                                type="text"
                                placeholder="Ваше ім'я"
                                value={reviewData[course.id]?.userName || ""}
                                onChange={(event) =>
                                    handleReviewChange(course.id, "userName", event.target.value)
                                }
                            />

                            <select
                                value={reviewData[course.id]?.rating || ""}
                                onChange={(event) =>
                                    handleReviewChange(course.id, "rating", event.target.value)
                                }
                            >
                                <option value="">Оцінка</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>

                            <textarea
                                placeholder="Текст відгуку"
                                value={reviewData[course.id]?.text || ""}
                                onChange={(event) =>
                                    handleReviewChange(course.id, "text", event.target.value)
                                }
                            />

                            <button onClick={() => addReview(course.id)}>
                                Додати відгук
                            </button>
                        </div>

                        <div className="reviews-list">
                            <h3>Відгуки</h3>

                            {course.reviews.length > 0 ? (
                                course.reviews.map((review, index) => (
                                    <div className="review" key={index}>
                                        <p>
                                            <strong>{review.userName}</strong> — {review.rating}/5
                                        </p>
                                        <p>{review.text}</p>
                                    </div>
                                ))
                            ) : (
                                <p className="empty">Відгуків ще немає.</p>
                            )}
                        </div>
                    </div>
                ))}
            </main>
        </div>
    );
}

export default App;