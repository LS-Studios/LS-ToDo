package de.check24.dualesstudium.stubbe.todo.view;

import java.io.BufferedReader;
import java.io.IOException;

public class TodoTerminalView implements TodoView {

    BufferedReader bufferedReader;

    public TodoTerminalView(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void write(String text) {
        System.out.println(text);
    }

    @Override
    public String read() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            write(e.getMessage());
        }

        return "";
    }

}
