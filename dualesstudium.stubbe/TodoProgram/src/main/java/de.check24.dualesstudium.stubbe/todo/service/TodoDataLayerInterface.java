package de.check24.dualesstudium.stubbe.todo.service;

import de.check24.dualesstudium.stubbe.todo.models.Todo;

import java.util.List;

public interface TodoDataLayerInterface {

    List<Todo> getAllTodos();

    Todo getTodoById(int id);

    Todo getTodoByName(String name);

    boolean deleteTodo(int id);

    boolean deleteAllTodos();

    boolean addTodo(Todo todo);

    boolean updateTodo(Todo todo);

    List<Todo> createTestDataIfNotExist();

}
