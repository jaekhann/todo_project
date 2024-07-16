package uz.pdp.g42.jaecoder.todo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.java.Log;
import uz.pdp.g42.jaecoder.dto.BaseResponse;
import uz.pdp.g42.jaecoder.dto.TodoCreateDto;
import uz.pdp.g42.jaecoder.dto.TodoUpdateDto;
import uz.pdp.g42.jaecoder.service.TodoService;
import uz.pdp.g42.jaecoder.utils.GsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Log
public class TodoController implements HttpHandler {
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private final TodoService todoService;
    private final Gson gson = GsonUtil.getGson();

    public TodoController() {
        this.todoService = new TodoService();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        log.info("Request received. uri : , " + httpExchange.getRequestURI() + ". Method: " + httpExchange.getRequestMethod());

        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        OutputStream os = httpExchange.getResponseBody();
        os.write(new GsonBuilder().setPrettyPrinting().create().toJson(todoService.getAll()).getBytes());
    }

    private void processPutRequest(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        InputStream inputStream = httpExchange.getRequestBody();
        TodoUpdateDto todoUpdateDto = GsonUtil.fromJson(inputStream, TodoUpdateDto.class);
        Todo todo = todoService.update(todoUpdateDto);
        byte[] bytes = GsonUtil.objectToByteArray(new BaseResponse<>(todo));
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        outputStream.write(bytes);
        outputStream.close();
    }

    private void processDeleteRequest(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        String uri = getPath(httpExchange);
        Long id = getPathVariable(uri);
        todoService.deleteById(id);
        BaseResponse<String> baseResponse = new BaseResponse<>("todo successfully deleted");
        byte[] bytes = GsonUtil.objectToByteArray(baseResponse);
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        outputStream.write(bytes);
        outputStream.close();
    }

    private void processPostRequest(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        InputStream inputStream = httpExchange.getRequestBody();
        TodoCreateDto todoCreateDto = GsonUtil.fromJson(inputStream, TodoCreateDto.class);
        Todo todo = todoService.create(todoCreateDto);
        BaseResponse<Todo> baseResponse = new BaseResponse<>(todo);
        byte[] bytes = GsonUtil.objectToByteArray(baseResponse);
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        outputStream.write(bytes);
        outputStream.close();
    }

    private void processGetRequest(HttpExchange httpExchange) throws IOException {
        String uri = getPath(httpExchange);
        OutputStream outputStream = httpExchange.getResponseBody();
        Object responseData;
        if (uri.equals("/todo")) {
            responseData = todoService.getAll();
        } else {
            Long id = getPathVariable(uri);
            responseData = todoService.getById(id);
        }
        BaseResponse<Object> baseResponse = new BaseResponse<>(responseData);
        byte[] bytes = GsonUtil.objectToByteArray(baseResponse);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        httpExchange.sendResponseHeaders(200, 0);
        outputStream.write(bytes);
        outputStream.close();
    }

    private void processUnhandledRequest(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        httpExchange.sendResponseHeaders(404, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        BaseResponse<Void> baseResponse = new BaseResponse<>("not found");
        outputStream.write(GsonUtil.objectToByteArray(baseResponse));
        outputStream.close();
    }

    private String getPath(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().getPath();
    }

    private Long getPathVariable(String uri) {
        return Long.parseLong(uri.split("/")[2]);
    }
}
