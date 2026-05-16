const { Product, Category, Review } = require("../models");

exports.getAllProducts = async (req, res) => {
    try {
        const products = await Product.findAll({
            include: [
                { model: Category, as: "category" },
                { model: Review, as: "reviews" }
            ]
        });

        res.status(200).json(products);
    } catch (error) {
        res.status(500).json({
            message: "Помилка отримання товарів",
            error: error.message
        });
    }
};

exports.getProductById = async (req, res) => {
    try {
        const product = await Product.findByPk(req.params.id, {
            include: [
                { model: Category, as: "category" },
                { model: Review, as: "reviews" }
            ]
        });

        if (!product) {
            return res.status(404).json({
                message: "Товар не знайдено"
            });
        }

        res.status(200).json(product);
    } catch (error) {
        res.status(500).json({
            message: "Помилка отримання товару",
            error: error.message
        });
    }
};

exports.createProduct = async (req, res) => {
    try {
        const product = await Product.create(req.body);

        res.status(201).json({
            message: "Товар створено",
            product
        });
    } catch (error) {
        res.status(500).json({
            message: "Помилка створення товару",
            error: error.message
        });
    }
};

exports.updateProduct = async (req, res) => {
    try {
        const product = await Product.findByPk(req.params.id);

        if (!product) {
            return res.status(404).json({
                message: "Товар не знайдено"
            });
        }

        await product.update(req.body);

        res.status(200).json({
            message: "Товар оновлено",
            product
        });
    } catch (error) {
        res.status(500).json({
            message: "Помилка оновлення товару",
            error: error.message
        });
    }
};

exports.deleteProduct = async (req, res) => {
    try {
        const product = await Product.findByPk(req.params.id);

        if (!product) {
            return res.status(404).json({
                message: "Товар не знайдено"
            });
        }

        await product.destroy();

        res.status(200).json({
            message: "Товар видалено"
        });
    } catch (error) {
        res.status(500).json({
            message: "Помилка видалення товару",
            error: error.message
        });
    }
};