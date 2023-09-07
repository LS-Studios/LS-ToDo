package de.check24.dualesstudium.stubbe.todo.service;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.database.service.DatabaseConnection;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import de.check24.dualesstudium.stubbe.todo.models.TodoHelper;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoDatabaseServiceTest {

    private DatabaseConnection dbConnection;
    private Connection connection;
    private TodoView view;

    @BeforeEach
    void setUp() {
        dbConnection = mock(DatabaseConnection.class);
        connection = mock(Connection.class);
        view = mock(TodoView.class);
    }

    @Test
    void getTodoByNameTest() throws SQLException {
        Todo expectedTodo = new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false);

        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString(Constants.TODO_NAME)).thenReturn(expectedTodo.getName());
        when(resultSet.getString(Constants.TODO_DESCRIPTION)).thenReturn(expectedTodo.getDescription());
        when(resultSet.getString(Constants.TODO_DUE_DATE)).thenReturn(TodoHelper.getDueDateAsString(expectedTodo.getDueDate()));
        when(resultSet.getInt(Constants.TODO_IS_DONE)).thenReturn(expectedTodo.isDone() ? 1 : 0);

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService(dbConnection, connection, view);
        Todo foundTodo = todoDatabaseService.getTodoByName("a");

        assertEquals(expectedTodo, foundTodo);
    }

    @Test
    void getTodoByNameExceptionTest() throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Test error"));

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService(dbConnection, connection, view);
        todoDatabaseService.getTodoByName("");

        verify(view, times(1)).write(any(String.class));
    }

    @Test
    void getTodoByIdTest() throws SQLException {
        Todo expectedTodo = new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false);

        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString(Constants.TODO_NAME)).thenReturn(expectedTodo.getName());
        when(resultSet.getString(Constants.TODO_DESCRIPTION)).thenReturn(expectedTodo.getDescription());
        when(resultSet.getString(Constants.TODO_DUE_DATE)).thenReturn(TodoHelper.getDueDateAsString(expectedTodo.getDueDate()));
        when(resultSet.getInt(Constants.TODO_IS_DONE)).thenReturn(expectedTodo.isDone() ? 1 : 0);

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService(dbConnection, connection, view);
        Todo foundTodo = todoDatabaseService.getTodoById(0);

        assertEquals(expectedTodo, foundTodo);
    }

    @Test
    void deleteTodoTest() throws SQLException {
        Todo expectedTodo = new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false);

        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService(dbConnection, connection, view);

        assertTrue(todoDatabaseService.deleteTodo(expectedTodo.getId()));
    }

    @Test
    void deleteTodoExceptionTest() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException("Test error"));

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService(dbConnection, connection, view);
        todoDatabaseService.deleteTodo(any(int.class));

        verify(view, times(1)).write(any(String.class));
    }

    @Test
    void deleteAllTodoTest() throws SQLException {
        Statement statement = mock(Statement.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.execute(any(String.class))).thenReturn(true);

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService(dbConnection, connection, view);

        assertTrue(todoDatabaseService.deleteAllTodos());
    }

    @Test
    void deleteAllTodoExceptionTest() throws SQLException {
        when(connection.createStatement()).thenThrow(new SQLException("Test error"));

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService(dbConnection, connection, view);
        todoDatabaseService.deleteAllTodos();

        verify(view, times(1)).write(any(String.class));
    }

    @Test
    void addTodoTest() throws SQLException {
        Todo expectedTodo = new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false);

        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService(dbConnection, connection, view);

        assertTrue(todoDatabaseService.addTodo(expectedTodo));
    }

    @Test
    void addTodoExceptionTest() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException("Test error"));

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService(dbConnection, connection, view);
        todoDatabaseService.addTodo(any(Todo.class));

        verify(view, times(1)).write(any(String.class));
    }

    @Test
    void updateTodoTest() throws SQLException {
        Todo expectedTodo = new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false);

        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString(Constants.TODO_NAME)).thenReturn(expectedTodo.getName());
        when(resultSet.getString(Constants.TODO_DESCRIPTION)).thenReturn(expectedTodo.getDescription());
        when(resultSet.getString(Constants.TODO_DUE_DATE)).thenReturn(TodoHelper.getDueDateAsString(expectedTodo.getDueDate()));
        when(resultSet.getInt(Constants.TODO_IS_DONE)).thenReturn(expectedTodo.isDone() ? 1 : 0);

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService(dbConnection, connection, view);

        assertTrue(todoDatabaseService.updateTodo(expectedTodo));
    }

}