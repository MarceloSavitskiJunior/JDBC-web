package com.unimater.dao;

import com.unimater.model.Entity;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class GenericDaoImpl<T extends Entity> implements GenericDao<T> {

    Connection connection;
    protected String tableName;
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
    public void upsert(T entity) {
        Field[] fields = entity.getClass().getDeclaredFields();

        if (entity.getId() == 0) {
           insertValue(fields, entity);
        } else {
           updateValues(fields, entity);
        }
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

    private void insertValue(Field[] fields, T entity) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder(" VALUES ");

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            sql.append(field.getName());
            values.append("?");

            if (i < fields.length - 1) {
                sql.append(", ");
                values.append(", ");
            }
        }

        sql.append(")").append(values).append(")");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);

                preparedStatement.setObject(i + 1, field.get(entity));
            }

            preparedStatement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void updateValues(Field[] fields, T entity) {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            sql.append(field.getName()).append(" = ?");

            if (i < fields.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(" WHERE id = ?");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);

                preparedStatement.setObject(i + 1, field.get(entity));
            }

            preparedStatement.setInt(fields.length + 1, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
