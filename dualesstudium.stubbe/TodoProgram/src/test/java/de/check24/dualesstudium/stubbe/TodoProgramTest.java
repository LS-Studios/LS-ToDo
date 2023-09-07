package de.check24.dualesstudium.stubbe;

import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class TodoProgramTest {
    private TodoProgram todoProgram;
    private TodoView view;
    private TodoDataLayerInterface todoDataLayerInterface;
    private MockedStatic<TodoProgram> mockedStatic;

    @BeforeEach
    void setUp() {
        this.view = mock(TodoView.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
        this.todoProgram = new TodoProgram();
        this.mockedStatic = mockedStatic = Mockito.mockStatic(TodoProgram.class);
    }

    @AfterEach
    void cleanUp() {
        mockedStatic.close();
    }

    @Test
    void testCommandCanNotBeFoundLoop() {
        //Test wrong command
        when(view.read()).thenReturn("No valid command").thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.COMMAND_CAN_NOT_BE_FOUND);
    }

    @Test
    void testCancelCommandLoop() {
        when(view.read()).thenReturn(Constants.CANCEL_COMMAND).thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.ENTER_COMMAND);
    }
    @Test
    void testCreateCommandLoop() {
        when(view.read()).thenReturn(Constants.CREATE_COMMAND).thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.ENTER_COMMAND);
    }
    @Test
    void testDeleteCommandLoop() {
        when(view.read()).thenReturn(Constants.DELETE_COMMAND).thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.ENTER_COMMAND);
    }
    @Test
    void testHelpAllCommandLoop() {
        when(view.read()).thenReturn(Constants.HELP_COMMAND).thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.ENTER_COMMAND);
    }
    @Test
    void testMakeDoneCommandLoop() {
        when(view.read()).thenReturn(Constants.MAKE_DONE_COMMAND).thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.ENTER_COMMAND);
    }
    @Test
    void testMakeNotDoneCommandLoop() {
        when(view.read()).thenReturn(Constants.MAKE_NOT_DONE_COMMAND).thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.ENTER_COMMAND);
    }
    @Test
    void testQuitCommandLoop() {
        when(view.read()).thenReturn(Constants.QUIT_COMMAND).thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.ENTER_COMMAND);
    }
    @Test
    void testShowALlCommandLoop() {
        when(view.read()).thenReturn(Constants.SHOW_ALL_COMMAND).thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.ENTER_COMMAND);
    }
    @Test
    void testShowDoneCommandLoop() {
        when(view.read()).thenReturn(Constants.SHOW_DONE_COMMAND).thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.ENTER_COMMAND);
    }
    @Test
    void testShowNotDoneCommandLoop() {
        when(view.read()).thenReturn(Constants.SHOW_NOT_DONE_COMMAND).thenReturn(Constants.CANCEL_COMMAND);

        mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
        this.todoProgram.runLoop(view);

        verify(view, times(1)).write(Constants.ENTER_COMMAND);
    }

}
