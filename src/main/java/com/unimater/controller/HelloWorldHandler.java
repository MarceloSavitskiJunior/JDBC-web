package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HelloWorldHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        String metodo = httpExchange.getRequestMethod();

        direcionarRequestPeloMetodo(metodo);

        byte[] helloWorldByte = "Hello World".getBytes();

        httpExchange.sendResponseHeaders(200, helloWorldByte.length);

        outputStream.write(helloWorldByte);
        outputStream.close();
    }

    private void direcionarRequestPeloMetodo(String metodo) {
        switch (metodo) {
            case "POST" :   {fazerMetodoPOST(); break;}
            case "GET" :    {fazerMetodoGET(); break;}
            case "PUT" :    {fazerMetodoPUT(); break;}
            case "DELETE" : {fazerMetodoDELETE(); break;}
        }
    }

    private void fazerMetodoPOST() {
        System.out.println("Fazendo Método POST");
    }

    private void fazerMetodoGET() {
        System.out.println("Fazendo Método GET");
    }

    private void fazerMetodoPUT() {
        System.out.println("Fazendo Método PUT");
    }

    private void fazerMetodoDELETE() {
        System.out.println("Fazendo Método DELETE");
    }
}
