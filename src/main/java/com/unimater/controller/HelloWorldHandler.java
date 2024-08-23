package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HelloWorldHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        System.out.println("Bati no coiso");
        byte[] helloWorldByte = "Hello World".getBytes();

        httpExchange.sendResponseHeaders(200, helloWorldByte.length);

        outputStream.write(helloWorldByte);
        outputStream.close();
    }
}
