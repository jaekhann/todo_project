package uz.pdp.g42.jaecoder.service;

import uz.pdp.g42.jaecoder.dto.TodoCreateDto;
import uz.pdp.g42.jaecoder.dto.TodoUpdateDto;
import uz.pdp.g42.jaecoder.exceptions.NotFoundException;
import uz.pdp.g42.jaecoder.todo.Todo;
import uz.pdp.g42.jaecoder.todo.TodoDAO;

import java.util.List;
import java.util.Objects;

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

    public Todo update(TodoUpdateDto dto) {
        Todo todo = todoDAO.findById(dto.id())
                .orElseThrow(() -> new NotFoundException("todo not found: " + dto.id()));
        if (Objects.nonNull(dto.title())) {
            todo.setTitle(dto.title());
        }
        if (Objects.nonNull(dto.description())) {
            todo.setDescription(dto.description());
        }
        if (Objects.nonNull(dto.priority())) {
            todo.setPriority(dto.priority());
        }
        if (Objects.nonNull(dto.completed())) {
            todo.setDone(dto.completed());
        }
        return todoDAO.save(todo);
    }

    public Todo getById(Long id) {
        return todoDAO.findById(id)
                .orElseThrow(() -> new IllegalStateException("todo not found:" + id));
    }

    public List<Todo> getAll() {
        return todoDAO.findAll();
    }

    public void deleteById(Long id) {
        todoDAO.deleteById(id);
    }

}
