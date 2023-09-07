package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.actions.model.Actions;
import de.check24.dualesstudium.stubbe.actions.service.ActionHelper;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import de.check24.dualesstudium.stubbe.todo.models.TodoHelper;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

import java.time.LocalDate;

public class CreateAction extends AbstractAction {

    public CreateAction(TodoDataLayerInterface todoDataLayerInterface, TodoView view, String command) {
        super(todoDataLayerInterface, view, command);
    }

    @Override
    public void run() {
        //Ask for name
        view.write(Constants.NAME_OF_TODO);

        String enteredName = view.read();

        if (ActionHelper.cancelCheck(enteredName)) {
            Actions.CANCEL.getAction().action(view, command);
            return;
        }

        //Ask for the description
        view.write(Constants.DESCRIPTION_OF_TODO);

        String enteredDescription = view.read();

        if (ActionHelper.cancelCheck(enteredDescription)) {
            Actions.CANCEL.getAction().action(view, command);
            return;
        }

        //Ask for due date
        String enteredDueDate = askForDate();

        if (enteredDueDate == null || ActionHelper.cancelCheck(enteredDueDate)) {
            Actions.CANCEL.getAction().action(view, command);
            return;
        }

        LocalDate date = enteredDueDate.isEmpty() ? null : TodoHelper.getLocaleDateFromStringDate(enteredDueDate);

        todoDataLayerInterface.addTodo(new Todo(0, enteredName, enteredDescription, date, false));

        view.write(String.format(Constants.SUCCESSFULLY_ADDED_STATEMENT, enteredName));
    }

    private String askForDate() {
        view.write(Constants.DUE_DATE_OF_TODO);

        int alreadyAskedTimes = 0;

        String enteredDueDate = view.read().trim();

        while (alreadyAskedTimes < Constants.ASK_AGAIN_TIMES && !enteredDueDate.isEmpty() &&
                !ActionHelper.checkDateValidity(enteredDueDate) && !ActionHelper.cancelCheck(enteredDueDate)) {
            alreadyAskedTimes++;

            view.write(Constants.DUE_DATE_NOT_VALID);

            enteredDueDate = view.read();

            if (enteredDueDate.equals("")) {
                break;
            }
        }

        if (alreadyAskedTimes >= Constants.ASK_AGAIN_TIMES) {
            view.write(Constants.CANCEL_BECAUSE_TO_MANY_TIMES_WRONG);
            return null;
        }

        return enteredDueDate;
    }

}
