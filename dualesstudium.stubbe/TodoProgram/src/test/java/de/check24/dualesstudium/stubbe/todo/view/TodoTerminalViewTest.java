package de.check24.dualesstudium.stubbe.todo.view;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TodoTerminalViewTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private BufferedReader bufferedReader;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        bufferedReader = mock(BufferedReader.class);
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void writeTest() {
        TodoTerminalView todoTerminalView = new TodoTerminalView(bufferedReader);

        String toWrite = "Test write";

        todoTerminalView.write(toWrite);

        assertTrue(outContent.toString().contains(toWrite));
    }

    @Test
    void readExceptionTest() throws IOException {
        TodoTerminalView todoTerminalView = new TodoTerminalView(bufferedReader);

        when(bufferedReader.readLine()).thenThrow(IOException.class);

        String val = todoTerminalView.read();

        assertEquals("", val);
    }

    @Test
    void readTest() throws IOException {
        TodoTerminalView todoTerminalView = new TodoTerminalView(bufferedReader);

        String toWrite = "Test write";

        when(bufferedReader.readLine()).thenReturn(toWrite);

        assertTrue(todoTerminalView.read().contains(toWrite));
    }

}
