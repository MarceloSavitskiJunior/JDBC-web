package com.unimater.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductType implements Entity {

    private int id;
    private String description;

    public ProductType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public ProductType(String description) {
        this.description = description;
    }

    public ProductType() {
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ProductType(ResultSet rs) throws SQLException {
        super();
        this.id = rs.getInt("id");
        this.description = rs.getString("description");
    }

    @Override
    public Entity constructFromResultSet(ResultSet rs, Connection connection) throws SQLException {
        return new ProductType(rs);
    }

    @Override
    public String toString() {
        return id + " " + description;
    }

    @Override
    public PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, getDescription());
        return preparedStatement;
    }
}
