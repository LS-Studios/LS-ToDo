package de.check24.dualesstudium.stubbe;

import de.check24.dualesstudium.stubbe.actions.model.Actions;
import de.check24.dualesstudium.stubbe.database.service.DatabaseConnection;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.service.TodoDatabaseLayerInterface;
import de.check24.dualesstudium.stubbe.todo.service.TodoDatabaseService;
import de.check24.dualesstudium.stubbe.todo.service.TodoListService;
import de.check24.dualesstudium.stubbe.todo.view.TodoTerminalView;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class TodoProgram {

    //Create boolean variable to make the program run
    public static final AtomicBoolean isRun = new AtomicBoolean(true);
    private static TodoView globalTodoView;
    private static TodoDataLayerInterface globalTodoDataLayer;

    public static TodoView getGlobalTodoView() {
        if (globalTodoView == null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            globalTodoView = new TodoTerminalView(bufferedReader);
        }

        return globalTodoView;
    }

    public static TodoDataLayerInterface getGlobalTodoDataLayer() {
        if (globalTodoDataLayer == null) {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            globalTodoDataLayer = new TodoDatabaseService(dbConnection, dbConnection.getConnection(), getGlobalTodoView());
            ((TodoDatabaseService) globalTodoDataLayer).createTableIfNotExist();

            globalTodoDataLayer.createTestDataIfNotExist();
        }

//        if (globalTodoDataLayer == null) {
//            globalTodoDataLayer = new TodoListService(new ArrayList<>());
//        }

        return globalTodoDataLayer;
    }

    //Create lists for the todos and add default ones
    public static void main(String[] args) {
        TodoProgram todoProgram = new TodoProgram();
        todoProgram.run();
    }

    private void run() {
        while (isRun.get()) {
            runLoop(getGlobalTodoView());
        }
    }

    public void runLoop(TodoView view) {
        view.write(Constants.ENTER_COMMAND);
        String command = view.read();

        boolean commandFound = false;

        String commandSortEdit = command.toLowerCase().replace("sorted", "").trim();

        for (Actions action : Actions.values()) {
            if (commandSortEdit.equals(action.getCommand())) {
                action.getAction().action(view, command);
                commandFound = true;
                break;
            }
        }

        if (!commandFound) {
            view.write(Constants.COMMAND_CAN_NOT_BE_FOUND);
        }
        view.write(Constants.SEPARATOR);
    }
}