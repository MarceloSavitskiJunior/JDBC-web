package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.unimater.dao.impl.ProductDao;
import com.unimater.generic.GenericControllerImpl;
import com.unimater.model.Product;

import java.io.IOException;

public class ProductHandler extends GenericControllerImpl<ProductDao, Product> implements HttpHandler {

    public ProductHandler(ProductDao dao) {
        super(dao, Product.class);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String metodo = httpExchange.getRequestMethod();
        handleMethod(metodo, httpExchange);
    }
}
