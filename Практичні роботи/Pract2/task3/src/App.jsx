import React, { useState, useEffect } from "react";

export default function TaskBoard() {

  const [tasks, setTasks] = useState(() => {
    const saved = localStorage.getItem("tasks");
    return saved ? JSON.parse(saved) : [];
  });

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tag, setTag] = useState("Навчання");
  const [search, setSearch] = useState("");

  useEffect(() => {
    localStorage.setItem("tasks", JSON.stringify(tasks));
  }, [tasks]);

  const addTask = () => {

    if (!title.trim()) {
      alert("Введіть назву завдання");
      return;
    }

    const newTask = {
      id: Date.now(),
      title,
      description,
      tag,
      status: "Заплановано"
    };

    setTasks([...tasks, newTask]);

    setTitle("");
    setDescription("");
    setTag("Навчання");
  };

  const deleteTask = (id) => {
    setTasks(tasks.filter(task => task.id !== id));
  };

  const moveTask = (id, newStatus) => {

    setTasks(
      tasks.map(task =>
        task.id === id
          ? { ...task, status: newStatus }
          : task
      )
    );
  };

  const filteredTasks = tasks.filter(task =>
    task.title.toLowerCase().includes(search.toLowerCase()) ||
    task.tag.toLowerCase().includes(search.toLowerCase())
  );

  const statuses = [
    "Заплановано",
    "В процесі",
    "Виконано"
  ];

  return (

    <div className="container">

      <h1 className="title">
        Інтерактивна дошка завдань
      </h1>

      <div className="form-container">

        <div className="form-grid">

          <input
            type="text"
            placeholder="Назва завдання"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="input"
          />

          <select
            value={tag}
            onChange={(e) => setTag(e.target.value)}
            className="input"
          >
            <option>Навчання</option>
            <option>Робота</option>
            <option>Важливо</option>
            <option>Особисте</option>
          </select>

        </div>

        <textarea
          placeholder="Опис завдання"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="textarea"
        />

        <button
          onClick={addTask}
          className="add-button"
        >
          Додати завдання
        </button>

      </div>

      <div className="search-container">

        <input
          type="text"
          placeholder="Пошук за назвою або тегом"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="search-input"
        />

      </div>

      <div className="board">

        {statuses.map(status => (

          <div
            key={status}
            className="column"
          >

            <h2 className="column-title">
              {status}
            </h2>

            <div className="task-list">

              {filteredTasks
                .filter(task => task.status === status)
                .map(task => (

                  <div
                    key={task.id}
                    className="task-card"
                  >

                    <div className="task-header">

                      <h3 className="task-title">
                        {task.title}
                      </h3>

                      <button
                        onClick={() => deleteTask(task.id)}
                        className="delete-button"
                      >
                        Видалити
                      </button>

                    </div>

                    <p className="task-description">
                      {task.description}
                    </p>

                    <div className="task-footer">

                      <span className="tag">
                        {task.tag}
                      </span>

                    </div>

                    <div className="status-buttons">

                      {statuses.map(newStatus => (

                        newStatus !== task.status && (

                          <button
                            key={newStatus}
                            onClick={() => moveTask(task.id, newStatus)}
                            className="status-button"
                          >
                            {newStatus}
                          </button>

                        )

                      ))}

                    </div>

                  </div>

                ))}

            </div>

          </div>

        ))}

      </div>

    </div>

  );
}