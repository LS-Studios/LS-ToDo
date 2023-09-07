package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.actions.service.ActionHelper;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

public class MakeDoneAction extends AbstractAction {

    public MakeDoneAction(TodoDataLayerInterface todoDataLayerInterface, TodoView view, String command) {
        super(todoDataLayerInterface, view, command);
    }

    @Override
    public void run() {
        Todo foundTodo = ActionHelper.getTodoByEnteredName(Constants.MAKE_DONE_STATEMENT, todoDataLayerInterface, view);

        if (foundTodo != null) {
            foundTodo.setDone(true);
            todoDataLayerInterface.updateTodo(foundTodo);
            view.write(String.format(Constants.SUCCESSFULLY_STATEMENT, foundTodo.getName(), Constants.MADE_DONE_STATEMENT));
        }
    }

}
