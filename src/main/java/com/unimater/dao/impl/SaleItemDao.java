package com.unimater.dao.impl;

import com.unimater.dao.GenericDao;
import com.unimater.dao.GenericDaoImpl;
import com.unimater.model.SaleItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SaleItemDao extends GenericDaoImpl<SaleItem> implements GenericDao<SaleItem> {

    private Connection connection;
    private final String TABLE_NAME = "sale_item";
    private final List<String> COLUMNS = List.of("product_id", "quantity", "percentual_discount", "sale_id");

    public SaleItemDao(Connection connection) {
        super(SaleItem::new, connection);
        super.tableName = TABLE_NAME;
        super.columns = COLUMNS;
    }

    public List< SaleItem > findAllBySaleId(Connection connection, int saleId) throws SQLException {
        List< SaleItem > saleItems = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName + " WHERE sale_id = " + saleId);

        while (rs.next()) {
            SaleItem saleItem = new SaleItem(rs, connection);
            saleItems.add(saleItem);
        }

        return saleItems;
    }
}
