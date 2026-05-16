const { Order, User } = require("../models");

exports.getAllOrders = async (req, res) => {
    try {
        const orders = await Order.findAll({
            include: [{ model: User, as: "user" }]
        });

        res.status(200).json(orders);
    } catch (error) {
        res.status(500).json({
            message: "Помилка отримання замовлень",
            error: error.message
        });
    }
};

exports.getOrderById = async (req, res) => {
    try {
        const order = await Order.findByPk(req.params.id, {
            include: [{ model: User, as: "user" }]
        });

        if (!order) {
            return res.status(404).json({
                message: "Замовлення не знайдено"
            });
        }

        res.status(200).json(order);
    } catch (error) {
        res.status(500).json({
            message: "Помилка отримання замовлення",
            error: error.message
        });
    }
};

exports.createOrder = async (req, res) => {
    try {
        const order = await Order.create(req.body);

        res.status(201).json({
            message: "Замовлення створено",
            order
        });
    } catch (error) {
        res.status(500).json({
            message: "Помилка створення замовлення",
            error: error.message
        });
    }
};

exports.updateOrder = async (req, res) => {
    try {
        const order = await Order.findByPk(req.params.id);

        if (!order) {
            return res.status(404).json({
                message: "Замовлення не знайдено"
            });
        }

        await order.update(req.body);

        res.status(200).json({
            message: "Замовлення оновлено",
            order
        });
    } catch (error) {
        res.status(500).json({
            message: "Помилка оновлення замовлення",
            error: error.message
        });
    }
};

exports.deleteOrder = async (req, res) => {
    try {
        const order = await Order.findByPk(req.params.id);

        if (!order) {
            return res.status(404).json({
                message: "Замовлення не знайдено"
            });
        }

        await order.destroy();

        res.status(200).json({
            message: "Замовлення видалено"
        });
    } catch (error) {
        res.status(500).json({
            message: "Помилка видалення замовлення",
            error: error.message
        });
    }
};