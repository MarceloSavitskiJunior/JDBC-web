package com.unimater.dao.impl;

import com.unimater.dao.GenericDaoImpl;
import com.unimater.model.Product;

import java.sql.Connection;
import java.util.List;
import java.util.function.Supplier;

public class ProductDao extends GenericDaoImpl<Product> {

    private final String TABLE_NAME = "product";
    private final List<String> COLUMNS = List.of("product_type_id", "description", "value");

    public ProductDao(Connection connection) {
        super(Product::new, connection);
        this.tableName = TABLE_NAME;
        this.columns = COLUMNS;
    }
}
