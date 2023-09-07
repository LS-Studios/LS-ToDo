package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.TodoProgram;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

class ImportActionTest {

    private TodoView view;
    private TodoDataLayerInterface todoDataLayerInterface;
    private ImportAction importAction;

    @BeforeEach
    void setup() {
        this.view = mock(TodoView.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
        this.importAction= new ImportAction(todoDataLayerInterface, view, "import");
    }

    @Test
    void runWithRightFilePathTest() throws IOException {
        File testFile = new File("../test.json");
        testFile.createNewFile();

        when(view.read()).thenReturn("../test.json", "yes");

        importAction.run();

        testFile.delete();

        verify(view, times(3)).write(any(String.class));
    }

    @Test
    void runWithWrongFilePathTest() throws IOException {
        File testFile = new File("../test.xml");
        testFile.createNewFile();

        when(view.read()).thenReturn("/", "../test.xml", "yes");

        importAction.run();

        testFile.delete();

        verify(view, times(4)).write(any(String.class));
    }

    @Test
    void runWithWrongFilePathCancelTest() {
        when(view.read()).thenReturn("/", Constants.CANCEL_COMMAND);

        try (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            importAction.run();
        }

        verify(view, times(3)).write(any(String.class));
    }

    @Test
    void runWithWrongToManyFilePathsTest() {
        when(view.read()).thenReturn("/");

        importAction.run();

        verify(view, times(14)).write(any(String.class));
    }

}
