package com.unimater.dao;

import com.unimater.model.Sale;

import java.sql.Connection;

public class SaleDao extends GenericDaoImpl<Sale> implements GenericDao<Sale> {

    private Connection connection;
    private final String TABLE_NAME = "sale";

    public SaleDao(Connection connection) {
        super(Sale::new, connection);
        super.tableName = TABLE_NAME;
    }
}
