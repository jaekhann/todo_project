package uz.pdp.g42.jaecoder.todo;

import uz.pdp.g42.jaecoder.config.DataSourceConfig;
import uz.pdp.g42.jaecoder.config.SettingConfig;
import uz.pdp.g42.jaecoder.dto.TodoUpdateDto;
import uz.pdp.g42.jaecoder.exceptions.DataAccessException;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TodoDAO {
    private final Connection connection;
    private final String selectQuery = SettingConfig.get("query.todo.select");
    private final String selectAllQuery = SettingConfig.get("query.todo.select.all");
    private final String insertQuery = SettingConfig.get("query.todo.insert");
    private final String deleteQuery = SettingConfig.get("query.todo.delete");
    private final String updateQuery = SettingConfig.get("query.todo.update");
    private final TodoRowMapper todoRowMapper = new TodoRowMapper();

    public TodoDAO() {
        this.connection = DataSourceConfig.getConnection();
    }

    public Optional<Todo> findById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(todoRowMapper.toDomain(resultSet));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return Optional.empty();
    }

    public List<Todo> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            return todoRowMapper.toDomainList(resultSet);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Todo save(Todo todo) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getDescription());
            preparedStatement.setLong(3,todo.getUserId());
            preparedStatement.setBoolean(4, todo.getDone());
            preparedStatement.setString(5, todo.getPriority().name());
            preparedStatement.execute();
            return todo;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(TodoUpdateDto updateDto) {
        try {

            Todo prevTodo = findById(updateDto.id()).get();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            if (updateDto.title() != null) {
                prevTodo.setTitle(updateDto.title());
            }
            if (updateDto.description() != null) {
                prevTodo.setDescription(updateDto.description());
            }
            if (updateDto.done() != null) {
                prevTodo.setDone(updateDto.done());
            }

            preparedStatement.setString(1, prevTodo.getTitle());
            preparedStatement.setString(2, prevTodo.getDescription());
            preparedStatement.setBoolean(3, prevTodo.getDone());
            preparedStatement.setString(4, prevTodo.getPriority().name());
            preparedStatement.setLong(5, updateDto.id());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }


}
