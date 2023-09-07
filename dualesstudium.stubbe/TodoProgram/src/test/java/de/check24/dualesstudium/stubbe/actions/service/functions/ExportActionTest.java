package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.TodoProgram;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ExportActionTest {

    private TodoView view;
    private TodoDataLayerInterface todoDataLayerInterface;
    private ExportAction exportAction;

    @BeforeEach
    void setup() {
        this.view = mock(TodoView.class);
        this.todoDataLayerInterface = mock(TodoDataLayerInterface.class);
        this.exportAction = new ExportAction(todoDataLayerInterface, view, "export");
    }


    @Test
    void runOneRunJsonTest() {
        when(view.read()).thenReturn("../test.json");

        exportAction.run();

        verify(view, times(2)).write(any(String.class));
    }

    @Test
    void runOneRunXMLTest() {
        when(view.read()).thenReturn("../test.xml");

        exportAction.run();

        verify(view, times(2)).write(any(String.class));
    }

    @Test
    void runTwoRunTest() {
        when(view.read()).thenReturn("../", "test.json");

        exportAction.run();

        verify(view, times(3)).write(any(String.class));
    }

    @Test
    void runTwoRunCancelTest() {
        when(view.read()).thenReturn("../", Constants.CANCEL_COMMAND);

        try (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            exportAction.run();
        }

        verify(view, times(3)).write(any(String.class));
    }

    @Test
    void runThreeRunTest() {
        when(view.read()).thenReturn("../", "test", "json");

        exportAction.run();

        verify(view, times(4)).write(any(String.class));
    }

    @Test
    void runThreeTwoWrongFormatTest() {
        when(view.read()).thenReturn("../", "test", "jso", "json");

        exportAction.run();

        verify(view, times(5)).write(any(String.class));
    }

    @Test
    void runThreeTwoWrongFormatCancelTest() {
        when(view.read()).thenReturn("../", "test", "jso", Constants.CANCEL_COMMAND);

        try (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            exportAction.run();
        }

        verify(view, times(5)).write(any(String.class));
    }

    @Test
    void runThreeTwoToManyWrongFormatTest() {
        when(view.read()).thenReturn("../", "test", "jso");

        try (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            exportAction.run();
        }

        verify(view, times(17)).write(any(String.class));
    }

    @Test
    void xmlFileIsGettingCreatedTest() {
        when(view.read()).thenReturn("../test.xml");

        File preFile = new File("../test.xml");
        assertTrue(!preFile.isFile());

        try (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            exportAction.run();
        }

        File afterFile = new File("../test.xml");
        assertTrue(afterFile.isFile());
    }

    @Test
    void jsonFileIsGettingCreatedTest() {
        when(view.read()).thenReturn("../test.json");

        File preFile = new File("../test.json");
        assertTrue(!preFile.isFile());

        try (MockedStatic<TodoProgram> mockedStatic = Mockito.mockStatic(TodoProgram.class)) {
            mockedStatic.when(() -> TodoProgram.getGlobalTodoDataLayer()).thenReturn(todoDataLayerInterface);
            exportAction.run();
        }

        File afterFile = new File("../test.json");
        assertTrue(afterFile.isFile());
    }

    @AfterEach
    void tearDown() throws Exception {
        File fileJson = new File("../test.json");

        if (fileJson.isFile())
            fileJson.delete();

        File fileXml = new File("../test.xml");

        if (fileXml.isFile())
            fileXml.delete();
    }

}
