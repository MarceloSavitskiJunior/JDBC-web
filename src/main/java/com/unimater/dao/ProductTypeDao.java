package com.unimater.dao;

import com.unimater.model.ProductType;

import java.sql.Connection;

public class ProductTypeDao extends GenericDaoImpl<ProductType> implements GenericDao<ProductType> {

    private Connection connection;
    private final String TABLE_NAME = "product_type";

    public ProductTypeDao(Connection connection) {
        super(ProductType::new, connection);
        super.tableName = TABLE_NAME;
    }
}
