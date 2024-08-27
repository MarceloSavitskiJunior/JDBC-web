package com.unimater.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Entity {

    Entity constructFromResultSet(ResultSet resultSet, Connection connection) throws SQLException;
    int getId();
    void setId(int id);
    PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException;
}
