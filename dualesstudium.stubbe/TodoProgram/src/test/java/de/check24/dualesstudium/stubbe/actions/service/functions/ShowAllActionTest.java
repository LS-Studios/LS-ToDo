package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.actions.service.ActionHelper;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

class ShowAllActionTest {

    private TodoView view;
    private TodoDataLayerInterface todoDataLayerInterface;
    private MockedStatic<ActionHelper> utility;
    private ShowAllAction showAllAction;

    @BeforeEach
    void setup() {
        this.view = mock(TodoView.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
        this.showAllAction = new ShowAllAction(todoDataLayerInterface, view, "show all");
        this.utility = Mockito.mockStatic(ActionHelper.class);
    }

    @AfterEach
    void clean() {
        this.utility.close();
    }

    @Test
    void runTest() {
        Todo expectedTodoA = new Todo(0, "a", "Test a todo", LocalDate.of(2001, 1, 1), true);
        Todo expectedTodoB = new Todo(1, "b", "Test b todo", LocalDate.of(2001, 1, 2),false);
        Todo expectedTodoC = new Todo(2, "c", "Test c todo", LocalDate.of(2001, 1, 3),true);
        Todo expectedTodoD = new Todo(3, "d", "Test d todo", LocalDate.of(2001, 1, 4),false);

        when(todoDataLayerInterface.getAllTodos()).thenReturn(new ArrayList<>(Arrays.asList(
                expectedTodoA,
                expectedTodoB,
                expectedTodoC,
                expectedTodoD
        )));

        showAllAction.run();

        utility.verify(() -> ActionHelper.showTodo(any(Todo.class), eq(view)), times(4));
    }

}
