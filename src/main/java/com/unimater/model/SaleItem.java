    package com.unimater.model;

    import com.unimater.dao.impl.ProductDao;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

    public class SaleItem implements Entity {

        private int id;
        private Product product;
        private int quantity;
        private double percentualDiscount;
        private int saleId;

        public SaleItem(Product product, int quantity, double percentualDiscount, int sale) {
            this.product = product;
            this.quantity = quantity;
            this.percentualDiscount = percentualDiscount;
            this.saleId = sale;
        }

        public SaleItem() {
        }

        public SaleItem(ResultSet resultSet, Connection connection) throws SQLException {
            super();
            this.id = resultSet.getInt("id");
            this.product = getProduct(connection, id);
            this.quantity = resultSet.getInt("quantity");
            this.percentualDiscount = resultSet.getDouble("percentual_discount");
            this.saleId = resultSet.getInt("sale_id");
        }

        public SaleItem(int saleId) {
            this.saleId = saleId;
        }

        private Product getProduct(Connection connection, int productId) {
            ProductDao productDao = new ProductDao(connection);
            return productDao.getById(productId);
        }

        public Product getProduct() {
            return product;
        }

        public double getQuantity() {
            return quantity;
        }

        public double getPercentualDiscount() {
            return percentualDiscount;
        }

        @Override
        public Entity constructFromResultSet(ResultSet resultSet, Connection connection) throws SQLException {
            return new SaleItem(resultSet, connection);
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
            preparedStatement.setObject(1, getProduct().getId());
            preparedStatement.setDouble(2, getQuantity());
            preparedStatement.setDouble(3, getPercentualDiscount());
            return preparedStatement;
        }

        @Override
        public String toString() {
            return "SaleItem{" +
                    "id=" + id +
                    ", product=" + product +
                    ", quantity=" + quantity +
                    ", percentualDiscount=" + percentualDiscount +
                    '}';
        }
    }
