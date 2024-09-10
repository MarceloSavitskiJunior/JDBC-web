package com.unimater.generic;

import com.sun.net.httpserver.HttpExchange;

public interface GenericController {

    void handleMethod(String method, HttpExchange exchange);
    void doPost(HttpExchange exchange);
    void doGet(HttpExchange exchange);
    void doPut(HttpExchange exchange);
    void doDelete(HttpExchange exchange);
}
