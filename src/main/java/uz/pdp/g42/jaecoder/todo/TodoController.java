package uz.pdp.g42.jaecoder.todo;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import uz.pdp.g42.jaecoder.service.TodoService;

import java.io.IOException;
import java.io.OutputStream;

public class TodoController implements HttpHandler {
    private final TodoService todoService;

    public TodoController() {
        this.todoService = new TodoService();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        OutputStream os = httpExchange.getResponseBody();
        os.write(new GsonBuilder().setPrettyPrinting().create().toJson(todoService.getAll()).getBytes());
    }
}
