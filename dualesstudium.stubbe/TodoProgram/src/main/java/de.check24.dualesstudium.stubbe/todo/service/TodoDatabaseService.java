package de.check24.dualesstudium.stubbe.todo.service;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.database.service.DatabaseConnection;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import de.check24.dualesstudium.stubbe.todo.models.TodoHelper;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoDatabaseService implements TodoDatabaseLayerInterface {
    private final DatabaseConnection dbConnection;
    private final Connection connection;
    private final TodoView view;
    public TodoDatabaseService(DatabaseConnection dbConnection, Connection connection, TodoView view) {
        this.dbConnection = dbConnection;
        this.connection = connection;
        this.view = view;
    }
    @Override
    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();

        String sql = String.format("SELECT * FROM %s", Constants.TODO_TABLE_NAME);

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int todoId = resultSet.getInt(Constants.TODO_ID);
                String todoName = resultSet.getString(Constants.TODO_NAME);
                String todoDescription = resultSet.getString(Constants.TODO_DESCRIPTION);
                LocalDate todoDueDate = resultSet.getString(Constants.TODO_DUE_DATE).isEmpty() ? null :
                        TodoHelper.getLocaleDateFromStringDate(resultSet.getString(Constants.TODO_DUE_DATE));
                boolean todoIsDone = resultSet.getInt(Constants.TODO_IS_DONE) != 0;

                todos.add(
                        new Todo(todoId, todoName, todoDescription, todoDueDate, todoIsDone)
                );
            }
        } catch (SQLException e) {
            view.write(e.getMessage());
        }

        return todos;
    }

    @Override
    public Todo getTodoById(int id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", Constants.TODO_TABLE_NAME, Constants.TODO_ID);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String todoName = resultSet.getString(Constants.TODO_NAME);
                String todoDescription = resultSet.getString(Constants.TODO_DESCRIPTION);
                LocalDate todoDueDate = resultSet.getString(Constants.TODO_DUE_DATE).isEmpty() ? null :
                        TodoHelper.getLocaleDateFromStringDate(resultSet.getString(Constants.TODO_DUE_DATE));
                boolean todoIsDone = resultSet.getInt(Constants.TODO_IS_DONE) != 0;

                return new Todo(id, todoName, todoDescription, todoDueDate, todoIsDone);
            }
        } catch (SQLException e) {
            view.write(e.getMessage());
        }

        return null;
    }

    @Override
    public Todo getTodoByName(String name) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", Constants.TODO_TABLE_NAME, Constants.TODO_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int todoId = resultSet.getInt(Constants.TODO_ID);
                String todoName = resultSet.getString(Constants.TODO_NAME);
                String todoDescription = resultSet.getString(Constants.TODO_DESCRIPTION);
                LocalDate todoDueDate = resultSet.getString(Constants.TODO_DUE_DATE).isEmpty() ? null :
                        TodoHelper.getLocaleDateFromStringDate(resultSet.getString(Constants.TODO_DUE_DATE));
                boolean todoIsDone = resultSet.getInt(Constants.TODO_IS_DONE) != 0;

                return new Todo(todoId, todoName, todoDescription, todoDueDate, todoIsDone);
            }
        } catch (SQLException e) {
            view.write(e.getMessage());
        }

        return null;
    }

    @Override
    public boolean deleteTodo(int id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", Constants.TODO_TABLE_NAME, Constants.TODO_ID);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            return result == 1 || result == 2;

        } catch (SQLException e) {
            view.write(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteAllTodos() {
        String sql = String.format("DELETE FROM %s", Constants.TODO_TABLE_NAME);

        try (Statement statement = connection.createStatement()) {
            return statement.execute(sql);

        } catch (SQLException e) {
            view.write(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean addTodo(Todo todo) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                Constants.TODO_TABLE_NAME, Constants.TODO_NAME, Constants.TODO_DESCRIPTION, Constants.TODO_DUE_DATE, Constants.TODO_IS_DONE);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, todo.getName());
            preparedStatement.setString(2, todo.getDescription());
            preparedStatement.setString(3, todo.getDueDate() == null ? "" : TodoHelper.getDueDateAsString(todo.getDueDate()));
            preparedStatement.setInt(4, todo.isDone() ? 1 : 0);
            int result = preparedStatement.executeUpdate();
            return result == 1 || result == 2;

        } catch (SQLException e) {
            view.write(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updateTodo(Todo todo) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
                Constants.TODO_TABLE_NAME, Constants.TODO_DESCRIPTION, Constants.TODO_DUE_DATE, Constants.TODO_IS_DONE, Constants.TODO_ID);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, todo.getDescription());
            preparedStatement.setString(2, TodoHelper.getDueDateAsString(todo.getDueDate()));
            preparedStatement.setInt(3, todo.isDone() ? 1 : 0);
            preparedStatement.setInt(4, todo.getId());
            preparedStatement.executeUpdate();
            return getTodoById(todo.getId()).equals(todo);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Todo> createTestDataIfNotExist() {
        if (getTodoByName("Make kitchen") == null) {
            addTodo(new Todo(0, "Make kitchen", "Making the dishes and cleaning the kitchen floor.", LocalDate.of(2022, 8, 9), false));
        }

        if (getTodoByName("Cooking") == null) {
            addTodo(new Todo(1, "Cooking", "Cook some nice lunch for my family.", null, true));
        }

        if (getTodoByName("Watch Netflix") == null) {
            addTodo(new Todo(2, "Watch Netflix", "Watch all seasons of \"Stranger Things\" this evening.", LocalDate.of(2022, 6, 4), false));
        }

        return getAllTodos();
    }

    @Override
    public boolean createTableIfNotExist() {
        String sql = String.format("""
                    CREATE TABLE IF NOT EXISTS %s (
                     %s INTEGER PRIMARY KEY AUTOINCREMENT,
                     %s TEXT,
                     %s TEXT,
                     %s TEXT,
                     %s INTEGER
                    );""",
                Constants.TODO_TABLE_NAME,
                Constants.TODO_ID,
                Constants.TODO_NAME,
                Constants.TODO_DESCRIPTION,
                Constants.TODO_DUE_DATE,
                Constants.TODO_IS_DONE);

        try (Statement statement = connection.createStatement()){
            return statement.execute(sql);
        } catch (SQLException e) {
            view.write(e.getMessage());
        }

        return false;
    }

    public DatabaseConnection getDBConnection() {
        return dbConnection;
    }
}
