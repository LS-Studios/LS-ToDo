package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CancelActionTest {

    private TodoView view;
    private TodoDataLayerInterface todoDataLayerInterface;
    private CancelAction cancelAction;

    @BeforeEach
    void setup() {
        this.view = mock(TodoView.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
        this.cancelAction = new CancelAction(todoDataLayerInterface, view, "show not done");
    }

    @Test
    void runTest() {
        cancelAction.run();

        verify(view, times(1)).write(Constants.CANCELING);
    }

}
