package de.check24.dualesstudium.stubbe.todo.service;

import de.check24.dualesstudium.stubbe.todo.models.Todo;

import java.time.LocalDate;
import java.util.List;

public class TodoListService implements TodoDataLayerInterface {

    private final List<Todo> todoList;

    public TodoListService(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoList;
    }

    @Override
    public Todo getTodoById(int id) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                return todo;
            }
        }

        return null;
    }

    @Override
    public Todo getTodoByName(String name) {
        for (Todo todo : todoList) {
            if (todo.getName().equals(name)) {
                return todo;
            }
        }

        return null;
    }

    @Override
    public boolean deleteTodo(int id) {
        return todoList.remove(getTodoById(id));
    }

    @Override
    public boolean deleteAllTodos() {
        todoList.clear();

        return true;
    }

    @Override
    public boolean addTodo(Todo todo) {
        return todoList.add(todo);
    }

    @Override
    public boolean updateTodo(Todo todo) {
        for (int i = 0 ; i < todoList.size() ; i++) {
            if (todoList.get(i).getId() == todo.getId()) {
                todoList.set(i, todo);
                return true;
            }
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

        return todoList;
    }

}
