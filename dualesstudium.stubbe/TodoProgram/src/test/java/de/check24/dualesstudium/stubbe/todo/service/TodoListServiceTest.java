package de.check24.dualesstudium.stubbe.todo.service;

import de.check24.dualesstudium.stubbe.todo.models.Todo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoListServiceTest {

    @Test
    void getAllTodosTest() {
        List<Todo> list = new ArrayList<>(Arrays.asList(
                new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false),
                new Todo(1, "b", "Test a todo", null, true),
                new Todo(2, "c", "Test a todo", LocalDate.of(2022, 6, 4), false)
        ));

        TodoListService todoListService = new TodoListService(list);

        Collection<Todo> collection = todoListService.getAllTodos();

        assertTrue(collection.containsAll(list));
    }

    @Test
    void getTodoByIdTest() {
        List<Todo> list = new ArrayList<>(Arrays.asList(
                new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false),
                new Todo(1, "b", "Test a todo", null, true),
                new Todo(2, "c", "Test a todo", LocalDate.of(2022, 6, 4), false)
        ));

        TodoListService todoListService = new TodoListService(list);

        Todo todo = todoListService.getTodoById(1);

        assertEquals("b", todo.getName());
    }

    @Test
    void getTodoByNameTest() {
        List<Todo> list = new ArrayList<>(Arrays.asList(
                new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false),
                new Todo(1, "b", "Test a todo", null, true),
                new Todo(2, "c", "Test a todo", LocalDate.of(2022, 6, 4), false)
        ));

        TodoListService todoListService = new TodoListService(list);

        Todo todo = todoListService.getTodoByName("c");

        assertEquals(2, todo.getId());
    }

    @Test
    void deleteTodoTest() {
        List<Todo> list = new ArrayList<>(Arrays.asList(
                new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false),
                new Todo(1, "b", "Test a todo", null, true),
                new Todo(2, "c", "Test a todo", LocalDate.of(2022, 6, 4), false)
        ));

        TodoListService todoListService = new TodoListService(list);

        todoListService.deleteTodo(list.get(1).getId());

        assertEquals(2, todoListService.getAllTodos().size());
    }

    @Test
    void addTodoTest() {
        List<Todo> list = new ArrayList<>(Arrays.asList(
                new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false),
                new Todo(1, "b", "Test a todo", null, true),
                new Todo(2, "c", "Test a todo", LocalDate.of(2022, 6, 4), false)
        ));

        TodoListService todoListService = new TodoListService(list);

        todoListService.addTodo(new Todo(3, "d", "Test d todo", null, true));

        assertEquals(4, todoListService.getAllTodos().size());
    }

    @Test
    void updateTodoTest() {
        List<Todo> list = new ArrayList<>(Arrays.asList(
                new Todo(0, "a", "Test a todo", LocalDate.of(2022, 8, 9), false),
                new Todo(1, "b", "Test a todo", null, true),
                new Todo(2, "c", "Test a todo", LocalDate.of(2022, 6, 4), false)
        ));

        TodoListService todoListService = new TodoListService(list);

        Todo todo = list.get(1);
        todo.setDone(false);

        todoListService.updateTodo(todo);

        assertFalse(todoListService.getTodoById(1).isDone());
    }

    @Test
    void createTestDataIfNotExistTest() {
        List<Todo> list = new ArrayList<>();

        TodoListService todoListService = new TodoListService(list);
        todoListService.createTestDataIfNotExist();

        assertTrue(todoListService.getAllTodos().size() > 0);
    }

}