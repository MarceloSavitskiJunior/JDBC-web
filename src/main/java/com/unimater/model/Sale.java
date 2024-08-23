package com.unimater.model;

import com.unimater.dao.impl.SaleItemDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sale implements Entity {

    private int id;
    private List<SaleItem> itens;
    private Timestamp createdAt;

    public Sale() {
        itens = new ArrayList<>();
    }

    public List<SaleItem> getItens() {
        return itens;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Sale(ResultSet resultSet, Connection connection) throws SQLException {
        super();
        this.id = resultSet.getInt("id");
        this.itens = getSaleItens(connection, id);
        this.createdAt = resultSet.getTimestamp("insert_at");
    }

    private List< SaleItem > getSaleItens(Connection connection, int saleId) throws SQLException {
        SaleItemDao dao = new SaleItemDao(connection);
        return dao.findAllBySaleId(connection, saleId);
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
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", itens=" + itens +
                ", createdAt=" + createdAt +
                '}';
    }
}
