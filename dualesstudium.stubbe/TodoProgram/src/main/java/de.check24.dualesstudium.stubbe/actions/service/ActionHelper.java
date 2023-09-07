package de.check24.dualesstudium.stubbe.actions.service;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.actions.model.Actions;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import de.check24.dualesstudium.stubbe.todo.models.TodoHelper;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ActionHelper {

    private ActionHelper() {}

    public static Todo getTodoByEnteredName(String actionName, TodoDataLayerInterface todoDataLayerInterface, TodoView view) {
        view.write(String.format("Name of the todo to %s:", actionName));

        String enteredName = view.read();

        if (cancelCheck(enteredName)) {
            Actions.CANCEL.getAction().action(view, Constants.CANCEL_COMMAND);
            return null;
        }

        Todo todoToFind = todoDataLayerInterface.getTodoByName(enteredName);

        int alreadyAskedTimes = 0;

        while (alreadyAskedTimes < Constants.ASK_AGAIN_TIMES && todoToFind == null) {
            alreadyAskedTimes++;

            view.write(Constants.NO_TODO_WITH_ENTERED_NAME_STATEMENT);

            enteredName = view.read();

            if (cancelCheck(enteredName)) {
                Actions.CANCEL.getAction().action(view, Constants.CANCEL_COMMAND);
                return null;
            }

            todoToFind = todoDataLayerInterface.getTodoByName(enteredName);
        }

        if (alreadyAskedTimes >= Constants.ASK_AGAIN_TIMES) {
            view.write(Constants.CANCEL_BECAUSE_TO_MANY_TIMES_WRONG);
            return null;
        }

        return todoToFind;
    }

    public static boolean askForYesNoAnswer(String askText, TodoView view) {
        view.write(askText);

        int alreadyAskedTimes = 0;

        String enteredWantToDelete = view.read();

        while (alreadyAskedTimes < Constants.ASK_AGAIN_TIMES && !enteredWantToDelete.equalsIgnoreCase("yes") && !enteredWantToDelete.equalsIgnoreCase("no")) {
            if (alreadyAskedTimes > 0) {
                view.write(Constants.ANSWER_NOT_VALID);
            }

            enteredWantToDelete = view.read();

            if (enteredWantToDelete.equalsIgnoreCase("no")) {
                return false;
            }

            if (cancelCheck(enteredWantToDelete)) {
                Actions.CANCEL.getAction().action(view, Constants.CANCEL_COMMAND);
                return false;
            }

            alreadyAskedTimes++;
        }

        return true;
    }

    public static List<Todo> sortTodo(List<Todo> todos) {
        List<Todo> sortedTodos = new ArrayList<>();

        //Add just the todos which have a name to the list to sort
        for (Todo todo : todos) {
            if (todo.getDueDate() != null) {
                sortedTodos.add(todo);
            }
        }

        //Sort the list
        sortedTodos.sort(Comparator.comparing(Todo::getDueDate));

        //Add the rest of the todos to the list that will get displayed
        for (Todo todo : todos) {
            if (todo.getDueDate() == null) {
                sortedTodos.add(todo);
            }
        }

        return sortedTodos;
    }

    public static String showTodo(Todo todo, TodoView view) {
        String show = String.format("%s%nName:        %s %nDescription: %s%s%nIs done:     %s",
                Constants.SEPARATOR,
                todo.getName(),
                todo.getDescription(),
                todo.getDueDate() == null ?
                    "" :
                    "\nDate:        " + TodoHelper.getDueDateAsString(todo.getDueDate()),
                TodoHelper.getDoneAsString(todo.isDone()));
        view.write(show);

        return show;
    }

    public static boolean checkDateValidity(String date) {
        try {
            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
            TodoHelper.getLocaleDateFromStringDate(date);
        } catch (DateTimeParseException e) {
            return false;
        }

        if (TodoHelper.getLocaleDateFromStringDate(date).isBefore(LocalDate.now())) {
            return false;
        }

        return true;
    }

    public static boolean cancelCheck(String command) {
        return command.equals(Actions.CANCEL.getCommand());
    }

}