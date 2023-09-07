package de.check24.dualesstudium.stubbe.actions.service;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.TodoProgram;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ActionHelperTest {

    private TodoView view;
    private TodoDataLayerInterface todoDataLayerInterface;

    @BeforeEach
    void setup() {
        this.view = mock(TodoView.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
    }

    @Test
    void getTodoByEnteredNameTest() {
        Todo expectedTodoA = new Todo(0,"a", "Test a todo", LocalDate.of(2001, 1, 1), true);
        Todo expectedTodoB = new Todo(1,"b", "Test b todo", LocalDate.of(2001, 1, 2),false);
        Todo expectedTodoC = new Todo(2,"c", "Test c todo", LocalDate.of(2001, 1, 3),true);
        Todo expectedTodoD = new Todo(3,"d", "Test d todo", LocalDate.of(2001, 1, 4),false);
        Todo expectedTodoE = new Todo(4,"e", "Test e todo", LocalDate.of(2001, 1, 5),true);

        when(view.read()).thenReturn("a");
        when(todoDataLayerInterface.getTodoByName("a")).thenReturn(expectedTodoA);
        Todo foundTodoA = ActionHelper.getTodoByEnteredName("1", todoDataLayerInterface, view);

        assertEquals(expectedTodoA, foundTodoA);

        when(view.read()).thenReturn("b");
        when(todoDataLayerInterface.getTodoByName("b")).thenReturn(expectedTodoB);
        Todo foundTodoB = ActionHelper.getTodoByEnteredName("2", todoDataLayerInterface, view);

        assertEquals(expectedTodoB, foundTodoB);

        when(view.read()).thenReturn("c");
        when(todoDataLayerInterface.getTodoByName("c")).thenReturn(expectedTodoC);
        Todo foundTodoC = ActionHelper.getTodoByEnteredName("3", todoDataLayerInterface, view);

        assertEquals(expectedTodoC, foundTodoC);

        when(view.read()).thenReturn("d");
        when(todoDataLayerInterface.getTodoByName("d")).thenReturn(expectedTodoD);
        Todo foundTodoD = ActionHelper.getTodoByEnteredName("4", todoDataLayerInterface, view);

        assertEquals(expectedTodoD, foundTodoD);

        when(view.read()).thenReturn("e");
        when(todoDataLayerInterface.getTodoByName("e")).thenReturn(expectedTodoE);
        Todo foundTodoE = ActionHelper.getTodoByEnteredName("5", todoDataLayerInterface, view);

        assertEquals(expectedTodoE, foundTodoE);
    }

    @Test
    void askForYesNoAnswerTest() {
        when(view.read()).thenReturn("yes");

        ActionHelper.askForYesNoAnswer("", view);

        verify(view, times(1)).write(any(String.class));
    }

    @Test
    void askForYesNoWrongAnswerTest() {
        when(view.read()).thenReturn("lol", "yes");

        ActionHelper.askForYesNoAnswer("", view);

        verify(view, times(1)).write(any(String.class));
    }

    @Test
    void askForYesNoWrongCancelAnswerTest() {
        when(view.read()).thenReturn("lol", Constants.CANCEL_COMMAND);

        try  (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            ActionHelper.askForYesNoAnswer("", view);
        }

        verify(view, times(2)).write(any(String.class));
    }

    @Test
    void getTodoByEnteredNameWrongNameTest() {
        Todo expectedTodoA = new Todo(0,"a", "Test a todo", LocalDate.of(2001, 1, 1), true);
        Todo expectedTodoB = new Todo(1, "b", "Test b todo", LocalDate.of(2001, 1, 2),false);
        Todo expectedTodoC = new Todo(2, "c", "Test c todo", LocalDate.of(2001, 1, 3),true);
        Todo expectedTodoD = new Todo(3, "d", "Test d todo", LocalDate.of(2001, 1, 4),false);
        Todo expectedTodoE = new Todo(4,"e", "Test e todo", LocalDate.of(2001, 1, 5),true);

        when(view.read()).thenReturn("a");
        when(todoDataLayerInterface.getTodoByName("a")).thenReturn(expectedTodoA);
        Todo foundTodoA = ActionHelper.getTodoByEnteredName("1", todoDataLayerInterface, view);

        assertEquals(expectedTodoA, foundTodoA);

        when(view.read()).thenReturn("b");
        when(todoDataLayerInterface.getTodoByName("b")).thenReturn(expectedTodoB);
        Todo foundTodoB = ActionHelper.getTodoByEnteredName("2", todoDataLayerInterface, view);

        assertEquals(expectedTodoB, foundTodoB);

        when(view.read()).thenReturn("c");
        when(todoDataLayerInterface.getTodoByName("c")).thenReturn(expectedTodoC);
        Todo foundTodoC = ActionHelper.getTodoByEnteredName("3", todoDataLayerInterface, view);

        assertEquals(expectedTodoC, foundTodoC);

        when(view.read()).thenReturn("d");
        when(todoDataLayerInterface.getTodoByName("d")).thenReturn(expectedTodoD);
        Todo foundTodoD = ActionHelper.getTodoByEnteredName("4", todoDataLayerInterface, view);

        assertEquals(expectedTodoD, foundTodoD);

        when(view.read()).thenReturn("e");
        when(todoDataLayerInterface.getTodoByName("e")).thenReturn(expectedTodoE);
        Todo foundTodoE = ActionHelper.getTodoByEnteredName("5", todoDataLayerInterface, view);

        assertEquals(expectedTodoE, foundTodoE);

        verify(view, times(5)).write(any(String.class));
    }

    @Test
    void sortTodoTest() {
        Todo expectedTodo1 = new Todo(0, "a", "Test a todo", LocalDate.of(2001, 1, 1), true);
        Todo expectedTodo2 = new Todo(1, "b", "Test b todo", LocalDate.of(2001, 1, 14),false);
        Todo expectedTodo3 = new Todo(2, "c", "Test c todo", LocalDate.of(2001, 1, 22),true);
        Todo expectedTodo4 = new Todo(3, "d", "Test d todo", null,false);

        List<Todo> unsorted = new ArrayList<>(Arrays.asList(
                expectedTodo3,
                expectedTodo2,
                expectedTodo4,
                expectedTodo1
        ));
        List<Todo> expectedSorted = new ArrayList<>(Arrays.asList(
                expectedTodo1,
                expectedTodo2,
                expectedTodo3,
                expectedTodo4
        ));

        List<Todo> sorted = ActionHelper.sortTodo(unsorted);
        assertEquals(expectedSorted, sorted);
    }

    @Test
    void showTodoTest() {
        Todo expectedTodoA = new Todo(0, "a", "Test a todo", LocalDate.of(2001, 1, 1), true);
        Todo expectedTodoB = new Todo(1, "b", "Test b todo", null,false);

        String resultA = ActionHelper.showTodo(expectedTodoA, view);
        assertTrue(resultA.contains("Name:        a"));
        assertTrue(resultA.contains("Description: Test a todo"));
        assertTrue(resultA.contains("Date:        01.01.2001"));
        assertTrue(resultA.contains("Is done:     Yes"));

        String resultB = ActionHelper.showTodo(expectedTodoB, view);
        assertTrue(resultB.contains("Name:        b"));
        assertTrue(resultB.contains("Description: Test b todo"));
        assertFalse(resultB.contains("Date:"));
        assertTrue(resultB.contains("Is done:     No"));
    }

    @Test
    void checkDateValidityTest() {
        String dateA = "01.01.2001";
        String dateB = "1.1.2001";
        String dateC = "test";
        String dateD = "22.01.2099";

        boolean isValidA = ActionHelper.checkDateValidity(dateA);
        boolean isValidB = ActionHelper.checkDateValidity(dateB);
        boolean isValidC = ActionHelper.checkDateValidity(dateC);
        boolean isValidD = ActionHelper.checkDateValidity(dateD);

        assertFalse(isValidA);
        assertFalse(isValidB);
        assertFalse(isValidC);
        assertTrue(isValidD);
    }

    @Test
    void cancelCheckTest() {
        boolean checkResult1 = ActionHelper.cancelCheck(":cancel");
        assertTrue(checkResult1);

        boolean checkResult2 = ActionHelper.cancelCheck("help");
        assertFalse(checkResult2);
    }

}
