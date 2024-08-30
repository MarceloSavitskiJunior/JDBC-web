package com.unimater;

import com.sun.net.httpserver.HttpServer;
import com.unimater.controller.HelloWorldHandler;
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
                    "admin");
            HttpServer servidor = HttpServer.create(new InetSocketAddress(8081), 0);
            servidor.createContext("/helloWorld", new HelloWorldHandler());

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

            ProductTypeDao productTypeDao = new ProductTypeDao(connection);
            SaleItemDao saleItemDao = new SaleItemDao(connection);
            ProductDao productDao = new ProductDao(connection);
            SaleDao saleDao = new SaleDao(connection);

            saleDao.getById(1);
            // BUSCANDO VALORES
            System.out.println("________PRODUCT TYPE________");
            List< ProductType > productTypes = productTypeDao.getAll();
            productTypes.forEach(p -> System.out.println(p.toString()));

            breakLine();

            System.out.println("________SALE ITEM________");
            List< SaleItem > saleItens = saleItemDao.getAll();
            saleItens.forEach(si -> System.out.println(si.toString()));

            breakLine();

            System.out.println("________SALE________");
            List< Sale > sales = saleDao.getAll();
            sales.forEach(s -> System.out.println(s.toString()));

            breakLine();

            System.out.println("________PRODUCT________");
            List< Product > products = productDao.getAll();
            products.forEach(s -> System.out.println(s.toString()));

            // INSERINDO VALORES
            ProductType pt = productTypeDao.getById(1);

            Product produto = new Product(pt, "Novo produto", 12);
            productDao.upsert(produto);
            Sale salee = saleDao.upsert(new Sale());
            System.out.println(salee.toString());

            // PROCURAR SE TODOS MUDARAM
            System.out.println("****** VERIFICANDO SE OS OBJETOS FORAM SALVOS ******");
            productTypeDao.getAll().forEach(p -> System.out.println(p.toString()));
            saleItemDao.getAll().forEach(saleitens -> System.out.println(saleitens.toString()));
            saleDao.getAll().forEach(sale -> System.out.println(sale.toString()));
            productDao.getAll().forEach(product -> System.out.println(product.toString()));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void breakLine() {
        System.out.println("\n");
    }
}
