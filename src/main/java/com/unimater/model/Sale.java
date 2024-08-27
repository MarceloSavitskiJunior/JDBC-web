package com.unimater.model;

import com.unimater.dao.impl.SaleItemDao;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sale implements Entity {

    private int id;
    private Timestamp createdAt;

    public Sale() {
        createdAt = Timestamp.from(Instant.now());
    }

    public Sale(SaleItem saleItem) {
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Sale(ResultSet resultSet, Connection connection) throws SQLException {
        super();
        this.id = resultSet.getInt("id");
        this.createdAt = resultSet.getTimestamp("insert_at");
    }

    @Override
    public Entity constructFromResultSet(ResultSet resultSet, Connection connection) throws SQLException {
        return new Sale(resultSet, connection);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setTimestamp(1, getCreatedAt());
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                '}';
    }
}
