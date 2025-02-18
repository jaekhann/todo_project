package uz.pdp.g42.jaecoder.todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoRowMapper {
    public Todo toDomain(ResultSet resultSet) throws SQLException {

        return new Todo(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getLong("user_id"),
                resultSet.getBoolean("done"),
                Priority.valueOf(resultSet.getString("priority")),
                resultSet.getDate("created_at")
        );
    }

    public List<Todo> toDomainList(ResultSet resultSet) throws SQLException {
        List<Todo> todoList = new ArrayList<>();
        while (resultSet.next()) {
            todoList.add(toDomain(resultSet));
        }
        return todoList;
    }
}
