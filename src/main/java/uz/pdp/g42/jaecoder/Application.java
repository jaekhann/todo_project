package uz.pdp.g42.jaecoder;

import com.sun.net.httpserver.HttpServer;
import uz.pdp.g42.jaecoder.config.SettingConfig;
import uz.pdp.g42.jaecoder.todo.TodoController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(SettingConfig.get("server.port"));
        int concurrentRequest = Integer.parseInt(SettingConfig.get("concurrent.request"));
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/todo", new TodoController());
        httpServer.setExecutor(Executors.newFixedThreadPool(concurrentRequest));
        httpServer.start();
        logger.info("server started on port : " + port);
    }
}