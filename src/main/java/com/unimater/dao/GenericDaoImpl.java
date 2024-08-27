package com.unimater.dao;

import com.unimater.model.Entity;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class GenericDaoImpl<T extends Entity> implements GenericDao<T> {

    Connection connection;
    protected String tableName;
    protected List<String> columns;
    private Supplier<T> supplier;

    public GenericDaoImpl(Supplier<T> supplier, Connection connection) {
        this.supplier = supplier;
        this.connection = connection;
    }

    @Override
    public List<T> getAll() {
        List<T> clazz = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            while (rs.next()) {
                T data = (T) supplier.get().constructFromResultSet(rs, connection);
                clazz.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    @Override
    public T getById(int id) {
        T returningObject = null;
        try {

            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM " + tableName + " WHERE id = ?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                returningObject = (T) supplier.get().constructFromResultSet(resultSet, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return returningObject;
    }

    @Override
    public T upsert(T object) {
        try {
            PreparedStatement pstmt;
            ResultSet rs;
            if (object.getId() == 0) { // Novo registro
                pstmt = connection.prepareStatement(
                        "INSERT INTO " + tableName + " ("
                                + columns.stream().collect(Collectors.joining(", "))
                                + ") VALUES ("
                                + columns.stream().map(item -> "?").collect(Collectors.joining(", "))
                                + ")"
                , Statement.RETURN_GENERATED_KEYS);
                pstmt = object.prepareStatement(pstmt);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    object.setId(rs.getInt(1));
                }
            } else { // Atualização de registro existente
                pstmt = connection.prepareStatement(
                        "UPDATE " + tableName + " SET "
                                + columns.stream().map(item -> item + " = ?").collect(Collectors.joining(", "))
                                + " WHERE id = ? RETURNING *"
                );
                pstmt = object.prepareStatement(pstmt);
                pstmt.setInt(columns.size() + 1, object.getId());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    object = (T) supplier.get().constructFromResultSet(rs, connection);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }


    @Override
    public void deleteById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("DELETE FROM " + tableName + " WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
