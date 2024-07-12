package uz.pdp.g42.jaecoder.service;

import uz.pdp.g42.jaecoder.dto.TodoCreateDto;
import uz.pdp.g42.jaecoder.dto.TodoUpdateDto;
import uz.pdp.g42.jaecoder.todo.Todo;
import uz.pdp.g42.jaecoder.todo.TodoDAO;

import java.util.List;

public class TodoService {
    private final TodoDAO todoDAO;

    public TodoService() {
        this.todoDAO = new TodoDAO();
    }

    public Todo create(TodoCreateDto dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.title());
        todo.setDescription(dto.description());
        todo.setUserId(dto.userId());
        todo.setPriority(dto.priority());
        return todo;
    }

    public void update(TodoUpdateDto dto) {
        Todo todo = todoDAO.findById(dto.id())
                .orElseThrow(() -> new IllegalStateException("todo not found:" + dto.id()));
        if (todo != null) {
            todoDAO.update(dto);
        }
    }

    public Todo getById(Long id) {
        return todoDAO.findById(id)
                .orElseThrow(() -> new IllegalStateException("todo not found:" + id));
    }

    public List<Todo> getAll() {
        return todoDAO.findAll();
    }


}
