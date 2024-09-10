package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.unimater.dao.ProductTypeDao;
import com.unimater.generic.GenericControllerImpl;

import java.io.IOException;

public class ProductTypeHandler extends GenericControllerImpl<ProductTypeDao> implements HttpHandler {

    public ProductTypeHandler(ProductTypeDao dao) {
        super(dao);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String metodo = httpExchange.getRequestMethod();
        handleMethod(metodo, httpExchange);
    }
}
