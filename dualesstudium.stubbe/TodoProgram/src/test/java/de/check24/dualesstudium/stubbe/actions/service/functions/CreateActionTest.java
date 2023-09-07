package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.TodoProgram;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class CreateActionTest {

    private TodoView view;
    private Todo todo;
    private TodoDataLayerInterface todoDataLayerInterface;
    private CreateAction createAction;

    @BeforeEach
    void setup() {
        this.view = mock(TodoView.class);
        this.todo = mock(Todo.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
        this.createAction = new CreateAction(todoDataLayerInterface, view, "create");
    }

    @Test
    void testRun() {
        when(view.read()).thenReturn("a", "Test a todo", "22.01.2099");

        try (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            createAction.run();
        }

        verify(view, times(1)).write((String.format(Constants.SUCCESSFULLY_ADDED_STATEMENT, "a")));
    }

    @Test
    void testNameAlreadyExist() {
        when(view.read()).thenReturn("a","e", "Test e todo", "22.01.2099");
        when(todoDataLayerInterface.getTodoByName("a")).thenReturn(todo);

        try (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            createAction.run();
        }

        verify(view, times(5)).write(any(String.class));
    }

    @Test
    void testNotValidDueDate() {
        when(view.read()).thenReturn("b", "Test b todo", "1.1.2001", "22.01.2099");
        when(todoDataLayerInterface.getTodoByName("b")).thenReturn(null);

        try (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            createAction.run();
        }

        verify(view, times(5)).write(any(String.class));
    }

    @Test
    void testToManyTimesWrongDueDate() {
        when(view.read()).thenReturn("b", "Test b todo", "1.1.2001");
        when(todoDataLayerInterface.getTodoByName("b")).thenReturn(null);

        try (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            createAction.run();
        }

        verify(view, times(17)).write(any(String.class));
    }

}
