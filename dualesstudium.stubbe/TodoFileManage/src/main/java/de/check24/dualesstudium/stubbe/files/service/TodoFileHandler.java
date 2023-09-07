package de.check24.dualesstudium.stubbe.files.service;

import de.check24.dualesstudium.stubbe.todo.models.Todo;

import java.util.List;

public abstract class TodoFileHandler {

    public abstract String getFileFormat();

    public abstract boolean exportTodo(List<Todo> todoList, String path);

    public abstract List<Todo> importTodo(String path) throws Exception;

}
