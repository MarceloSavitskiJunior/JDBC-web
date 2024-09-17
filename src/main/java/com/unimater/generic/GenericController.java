package com.unimater.generic;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface GenericController {

    void handleMethod(String method, HttpExchange exchange) throws IOException;
    void doPost(HttpExchange exchange) throws IOException;
    void doGet(HttpExchange exchange) throws IOException;
    void doPut(HttpExchange exchange) throws IOException;
    void doDelete(HttpExchange exchange) throws IOException;
}
