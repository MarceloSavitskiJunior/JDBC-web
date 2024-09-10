package com.unimater.generic;

import com.sun.net.httpserver.HttpExchange;
import com.unimater.dao.GenericDao;
import com.unimater.dao.GenericDaoImpl;
import com.unimater.model.Entity;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class GenericControllerImpl<T extends GenericDaoImpl> implements GenericController{

    protected Connection connection;
    protected T dao;

    private final String POST = "POST";
    private final String GET = "GET";
    private final String PUT = "PUT";
    private final String DELETE = "DELETE";

    public GenericControllerImpl(T dao) {
        this.dao = dao;
    }

    @Override
    public void handleMethod(String method, HttpExchange exchange) {
        switch (method) {
            case POST :   { doPost(exchange);   break; }
            case GET :    { doGet(exchange);    break; }
            case PUT :    { doPut(exchange);    break; }
            case DELETE : { doDelete(exchange); break; }
        }
    }

    @Override
    public void doDelete(HttpExchange exchange) {

    }

    @Override
    public void doGet(HttpExchange exchange) {
        try {
            System.out.println("Fazendo Método GET");

            byte[] first = dao.getById(1).toString().getBytes();
            exchange.sendResponseHeaders(200, first.length);

            OutputStream os = exchange.getResponseBody();
            os.write(first);
            os.flush();

            os.close();
            exchange.close();
        } catch (IOException e) {
            throw new RuntimeException("deu pau");
        }
    }

    @Override
    public void doPost(HttpExchange exchange) {

    }

    @Override
    public void doPut(HttpExchange exchange) {

    }
}
