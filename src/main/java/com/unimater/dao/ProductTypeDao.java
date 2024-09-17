package com.unimater.dao;

import com.unimater.model.ProductType;

import java.sql.Connection;
import java.util.List;

public class ProductTypeDao extends GenericDaoImpl<ProductType> implements GenericDao<ProductType> {

    private Connection connection;
    private final String TABLE_NAME = "product_type";
    private final List<String> COLUMNS = List.of("description");

    public ProductTypeDao(Connection connection) {
        super(ProductType::new, connection);
        super.tableName = TABLE_NAME;
        super.columns = COLUMNS;
    }
}
