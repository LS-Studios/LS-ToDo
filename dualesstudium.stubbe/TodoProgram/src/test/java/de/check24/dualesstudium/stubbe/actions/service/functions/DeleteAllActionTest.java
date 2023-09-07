package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.actions.service.ActionHelper;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class DeleteAllActionTest {

    private TodoView view;
    private TodoDataLayerInterface todoDataLayerInterface;
    private MockedStatic<ActionHelper> utility;
    private DeleteAllAction deleteAllAction;

    @BeforeEach
    void setup() {
        this.view = mock(TodoView.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
        this.deleteAllAction = new DeleteAllAction(todoDataLayerInterface, view, "delete all");
        this.utility = Mockito.mockStatic(ActionHelper.class);
    }

    @AfterEach
    void clean() {
        this.utility.close();
    }


    @Test
    void runYesTest() {
        utility.when(() -> ActionHelper.askForYesNoAnswer(any(String.class), eq(view))).thenReturn(true);

        deleteAllAction.run();

        verify(todoDataLayerInterface, times(1)).deleteAllTodos();
        verify(view, times(1)).write(any(String.class));
    }

    @Test
    void runNoTest() {
        utility.when(() -> ActionHelper.askForYesNoAnswer(any(String.class), eq(view))).thenReturn(false);

        deleteAllAction.run();

        verify(todoDataLayerInterface, times(0)).deleteAllTodos();
        verify(view, times(0)).write(any(String.class));
    }

}
