package com.unimater;

import com.sun.net.httpserver.HttpServer;
import com.unimater.controller.*;
import com.unimater.dao.ProductTypeDao;
import com.unimater.dao.SaleDao;
import com.unimater.dao.impl.ProductDao;
import com.unimater.dao.impl.SaleItemDao;
import com.unimater.model.Product;
import com.unimater.model.ProductType;
import com.unimater.model.Sale;
import com.unimater.model.SaleItem;
import com.unimater.service.GenericService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/minhabase",
                    "postgres",
                    "ademiro");

            ProductTypeDao productTypeDao = new ProductTypeDao(connection);
            SaleItemDao saleItemDao = new SaleItemDao(connection);
            ProductDao productDao = new ProductDao(connection);
            SaleDao saleDao = new SaleDao(connection);

            HttpServer servidor = HttpServer.create(new InetSocketAddress(8081), 0);
            servidor.createContext("/helloWorld", new HelloWorldHandler());
            servidor.createContext("/product-type", new ProductTypeHandler(productTypeDao));
            servidor.createContext("/product", new ProductHandler(productDao));
            servidor.createContext("/sale-item", new SaleItemHandler(saleItemDao));
            servidor.createContext("/sale", new SaleHandler(saleDao));

            servidor.setExecutor(null);
            // uri = tudo, desde o http até o último dígito: http://google.com/buscar
            // url = o que começa do http até aa primeira / : http://google.com/
            // host = entre a barra e o final da uri: google.com
            // o que é um executor? uma thread dentro do processador que tá rodando
            // thread = um bloco de execução
            // quando um servidor é startado, são reservadas threads no preocessador
            // toda vez que um processo acontece, faz o seguinte processo:
            // -> saí da placa mae
            // -> vai pro hd
            // -> vai pra ram
            // -> vai pra CPU
            // -> volta pro
            servidor.start();
            System.out.println("Servidor rodando na porta " + servidor.getAddress());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
