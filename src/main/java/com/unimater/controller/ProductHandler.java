package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.unimater.dao.impl.ProductDao;
import com.unimater.generic.GenericControllerImpl;

import java.io.IOException;
import java.sql.Connection;

public class ProductHandler extends GenericControllerImpl<ProductDao> implements HttpHandler {

    public ProductHandler(ProductDao dao) {
        super(dao);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String metodo = httpExchange.getRequestMethod();

        handleMethod(metodo, httpExchange);
        direcionarRequestPeloMetodo(metodo, httpExchange);
    }

    private void direcionarRequestPeloMetodo(String metodo, HttpExchange httpExchange) throws IOException {
        switch (metodo) {
            case "POST" :   { fazerMetodoPOST(httpExchange);   break; }
            case "GET" :    { fazerMetodoGET(httpExchange);    break; }
            case "PUT" :    { fazerMetodoPUT(httpExchange);    break; }
            case "DELETE" : { fazerMetodoDELETE(httpExchange); break; }
        }
    }

    private void fazerMetodoPOST(HttpExchange httpExchange) {
        httpExchange.getRequestBody();
        System.out.println("Fazendo Método POST");
    }

    private void fazerMetodoGET(HttpExchange httpExchange) {
        try {
            System.out.println("Fazendo Método GET");

            byte[] first = dao.getById(1).toString().getBytes();
            httpExchange.sendResponseHeaders(200, first.length);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void fazerMetodoPUT(HttpExchange httpExchange) {
        System.out.println("Fazendo Método PUT");
    }

    private void fazerMetodoDELETE(HttpExchange httpExchange) {
        System.out.println("Fazendo Método DELETE");
    }
}
