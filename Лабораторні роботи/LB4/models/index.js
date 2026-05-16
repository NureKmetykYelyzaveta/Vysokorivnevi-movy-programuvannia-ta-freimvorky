const sequelize = require("../config/database");

const User = require("./User");
const Category = require("./Category");
const Product = require("./Product");
const Order = require("./Order");
const Review = require("./Review");

User.hasMany(Order, {
    foreignKey: "user_id",
    as: "orders",
    onDelete: "CASCADE",
    onUpdate: "CASCADE"
});

Order.belongsTo(User, {
    foreignKey: "user_id",
    as: "user"
});

Category.hasMany(Product, {
    foreignKey: "category_id",
    as: "products",
    onDelete: "CASCADE",
    onUpdate: "CASCADE"
});

Product.belongsTo(Category, {
    foreignKey: "category_id",
    as: "category"
});

User.hasMany(Review, {
    foreignKey: "user_id",
    as: "reviews",
    onDelete: "CASCADE",
    onUpdate: "CASCADE"
});

Review.belongsTo(User, {
    foreignKey: "user_id",
    as: "user"
});

Product.hasMany(Review, {
    foreignKey: "product_id",
    as: "reviews",
    onDelete: "CASCADE",
    onUpdate: "CASCADE"
});

Review.belongsTo(Product, {
    foreignKey: "product_id",
    as: "product"
});

module.exports = {
    sequelize,
    User,
    Category,
    Product,
    Order,
    Review
};