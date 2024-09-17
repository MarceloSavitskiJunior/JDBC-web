package com.unimater.generic;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.unimater.dao.GenericDao;
import com.unimater.dao.GenericDaoImpl;
import com.unimater.model.Entity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class GenericControllerImpl<T extends GenericDaoImpl, E extends Entity> implements GenericController{

    protected Connection connection;
    protected T dao;
    protected Class<E> domain;
    private Gson gson = new Gson();

    private final String POST = "POST";
    private final String GET = "GET";
    private final String PUT = "PUT";
    private final String DELETE = "DELETE";

    public GenericControllerImpl(T dao, Class<E> domain) {
        this.dao = dao;
        this.domain = domain;
    }

    @Override
    public void handleMethod(String method, HttpExchange exchange) throws IOException {
        switch (method) {
            case POST :   { doPost(exchange);   break; }
            case GET :    { doGet(exchange);    break; }
            case PUT :    { doPut(exchange);    break; }
            case DELETE : { doDelete(exchange); break; }
        }
    }

    @Override
    public void doDelete(HttpExchange exchange) throws IOException {
        String[] segments = segmentPath(exchange);
        if (segments.length > 2) {
            try {
                int id = Integer.parseInt(segments[2]); // Parse the ID
                dao.deleteById(id); // Delete the product type
                exchange.sendResponseHeaders(204, -1); // No Content
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1); // Bad Request
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            exchange.sendResponseHeaders(404, -1); // Not Found
        }
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        String[] segments = segmentPath(exchange);
        if (segments.length > 2) {  // Adjust the index based on your URI structure
            try {
                int id = Integer.parseInt(segments[2]); // Parse the ID
                E clazz = (E) dao.getById(id);
                if (clazz != null) {
                    String jsonResponse = gson.toJson(clazz);
                    exchange.getResponseHeaders().add("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
                    sendOutputJson(exchange, jsonResponse);
                } else {
                    exchange.sendResponseHeaders(404, -1); // Not Found
                }
            } catch (NumberFormatException | IOException e) {
                exchange.sendResponseHeaders(400, -1); // Bad Request
            }
        } else {
            exchange.sendResponseHeaders(404, -1); // Not Found
        }
    }

    @Override
    public void doPost(HttpExchange exchange) throws IOException {
        E clazz = parseRequestBody(exchange, domain);
        if (clazz != null && clazz.getId() == 0) { // Assuming ID is 0 for new product
            dao.upsert(clazz); // Insert the new product type
            exchange.sendResponseHeaders(201, -1); // Created
        } else {
            exchange.sendResponseHeaders(400, -1); // Bad Request
        }
    }

    @Override
    public void doPut(HttpExchange exchange) throws IOException {
        E clazz = parseRequestBody(exchange, domain);
        if (clazz != null && clazz.getId() > 0) { // Existing product needs a valid ID
            dao.upsert(clazz); // Update the product type
            exchange.sendResponseHeaders(200, -1); // OK
        } else {
            exchange.sendResponseHeaders(400, -1); // Bad Request
        }
    }

    private static void sendOutputJson(HttpExchange exchange, String jsonResponse) throws IOException {
        OutputStream output = exchange.getResponseBody();
        output.write(jsonResponse.getBytes());
        output.flush();
        output.close();
    }

    private static String[] segmentPath(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] segments = path.split("/");
        return segments;
    }

    private <T> T parseRequestBody(HttpExchange exchange, Class<T> clazz) throws IOException {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        return gson.fromJson(reader, clazz);
    }
}
