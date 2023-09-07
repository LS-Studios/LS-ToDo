package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import de.check24.dualesstudium.stubbe.TodoProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuitActionTest {

    private TodoView view;
    private TodoDataLayerInterface todoDataLayerInterface;
    private QuitAction quitAction;

    @BeforeEach
    void setup() {
        this.view = mock(TodoView.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
        this.quitAction = new QuitAction(todoDataLayerInterface, view, "quit");
    }

    @Test
    void runTest() {
        quitAction.run();

        assertFalse(TodoProgram.isRun.get());
        verify(view, times(1)).write(any(String.class));
    }

}
