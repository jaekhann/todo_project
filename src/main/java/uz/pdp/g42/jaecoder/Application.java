package uz.pdp.g42.jaecoder;

import com.sun.net.httpserver.HttpServer;
import uz.pdp.g42.jaecoder.todo.TodoController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Application {
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(5433), 0);
        httpServer.createContext("/todo", new TodoController());
        httpServer.setExecutor(Executors.newFixedThreadPool(10));
    }
}