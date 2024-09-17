package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.unimater.dao.impl.ProductDao;
import com.unimater.dao.impl.SaleItemDao;
import com.unimater.generic.GenericControllerImpl;
import com.unimater.model.Product;
import com.unimater.model.SaleItem;

import java.io.IOException;

public class SaleItemHandler extends GenericControllerImpl<SaleItemDao, SaleItem> implements HttpHandler {

    public SaleItemHandler(SaleItemDao dao) {
        super(dao, SaleItem.class);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String metodo = httpExchange.getRequestMethod();
        handleMethod(metodo, httpExchange);
    }
}
