package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class HelpActionTest {

    private TodoView view;
    private TodoDataLayerInterface todoDataLayerInterface;
    private HelpAction helpAction;

    @BeforeEach
    void setup() {
        this.view = mock(TodoView.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
        this.helpAction = new HelpAction(todoDataLayerInterface, view, "help");
    }

    @Test
    void runTest() {
        when(view.read()).thenReturn("c");

        helpAction.run();

        verify(view, times(26)).write(any(String.class));
    }

}
