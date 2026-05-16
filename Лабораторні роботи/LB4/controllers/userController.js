const { User, Order, Review } = require("../models");

exports.getAllUsers = async (req, res) => {
    try {
        const users = await User.findAll({
            include: [
                { model: Order, as: "orders" },
                { model: Review, as: "reviews" }
            ]
        });

        res.status(200).json(users);
    } catch (error) {
        res.status(500).json({
            message: "Помилка отримання користувачів",
            error: error.message
        });
    }
};

exports.getUserById = async (req, res) => {
    try {
        const user = await User.findByPk(req.params.id, {
            include: [
                { model: Order, as: "orders" },
                { model: Review, as: "reviews" }
            ]
        });

        if (!user) {
            return res.status(404).json({
                message: "Користувача не знайдено"
            });
        }

        res.status(200).json(user);
    } catch (error) {
        res.status(500).json({
            message: "Помилка отримання користувача",
            error: error.message
        });
    }
};

exports.createUser = async (req, res) => {
    try {
        const user = await User.create(req.body);

        res.status(201).json({
            message: "Користувача створено",
            user
        });
    } catch (error) {
        res.status(500).json({
            message: "Помилка створення користувача",
            error: error.message
        });
    }
};

exports.updateUser = async (req, res) => {
    try {
        const user = await User.findByPk(req.params.id);

        if (!user) {
            return res.status(404).json({
                message: "Користувача не знайдено"
            });
        }

        await user.update(req.body);

        res.status(200).json({
            message: "Користувача оновлено",
            user
        });
    } catch (error) {
        res.status(500).json({
            message: "Помилка оновлення користувача",
            error: error.message
        });
    }
};

exports.deleteUser = async (req, res) => {
    try {
        const user = await User.findByPk(req.params.id);

        if (!user) {
            return res.status(404).json({
                message: "Користувача не знайдено"
            });
        }

        await user.destroy();

        res.status(200).json({
            message: "Користувача видалено"
        });
    } catch (error) {
        res.status(500).json({
            message: "Помилка видалення користувача",
            error: error.message
        });
    }
};