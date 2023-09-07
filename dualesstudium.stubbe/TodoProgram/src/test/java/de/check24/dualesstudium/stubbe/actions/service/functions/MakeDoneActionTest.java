package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.actions.service.ActionHelper;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class MakeDoneActionTest {

    private TodoView view;
    private Todo todo;
    private TodoDataLayerInterface todoDataLayerInterface;
    private MockedStatic<ActionHelper> utility;
    private MakeDoneAction makeDoneAction;

    @BeforeEach
    void setup() {
        this.view = mock(TodoView.class);
        this.todo = mock(Todo.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
        this.makeDoneAction = new MakeDoneAction(todoDataLayerInterface, view, "make done");
        this.utility = Mockito.mockStatic(ActionHelper.class);
    }

    @AfterEach
    void clean() {
        this.utility.close();
    }

    @Test
    void runTest() {
        when(view.read()).thenReturn("b");
        utility.when(() -> ActionHelper.getTodoByEnteredName("make done", todoDataLayerInterface, view)).thenReturn(todo);

        makeDoneAction.run();

        verify(todoDataLayerInterface, times(1)).updateTodo(todo);
        verify(view, times(1)).write(any(String.class));
    }

}
