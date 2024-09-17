package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.unimater.dao.SaleDao;
import com.unimater.dao.impl.SaleItemDao;
import com.unimater.generic.GenericControllerImpl;
import com.unimater.model.Sale;
import com.unimater.model.SaleItem;

import java.io.IOException;

public class SaleHandler extends GenericControllerImpl<SaleDao, Sale> implements HttpHandler {

    public SaleHandler(SaleDao dao) {
        super(dao, Sale.class);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String metodo = httpExchange.getRequestMethod();
        handleMethod(metodo, httpExchange);
    }
}
