package com.unimater.model;

import com.unimater.dao.ProductTypeDao;
import com.unimater.dao.impl.ProductDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product implements Entity {

    private int id;
    private ProductType productType;
    private String description;
    private double value;

    public Product(int id, ProductType productType, String description, double value) {
        this.id = id;
        this.productType = productType;
        this.description = description;
        this.value = value;
    }

    public Product(ProductType productType, String description, double value) {
        this.productType = productType;
        this.description = description;
        this.value = value;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setObject(1, getProductType().getId());
        preparedStatement.setString(2, getDescription());
        preparedStatement.setDouble(3, getValue());
        return preparedStatement;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getDescription() {
        return description;
    }

    public double getValue() {
        return value;
    }

    public Product(ResultSet resultSet, Connection connection) throws SQLException {
        super();
        this.id = resultSet.getInt("id");
        this.productType = getProductType(connection, resultSet.getInt("product_type_id"));
        this.description = resultSet.getString("description");
        this.value = resultSet.getDouble("value");
    }

    private ProductType getProductType(Connection connection, int productTypeId) {
        ProductTypeDao dao = new ProductTypeDao(connection);
        return dao.getById(productTypeId);
    }

    @Override
    public Entity constructFromResultSet(ResultSet resultSet, Connection connection) throws SQLException {
        return new Product(resultSet, connection);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productType=" + productType +
                ", description='" + description + '\'' +
                ", value=" + value +
                '}';
    }
}
