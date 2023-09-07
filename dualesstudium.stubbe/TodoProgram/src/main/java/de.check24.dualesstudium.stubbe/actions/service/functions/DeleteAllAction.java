package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.actions.service.ActionHelper;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

public class DeleteAllAction extends AbstractAction {

    public DeleteAllAction(TodoDataLayerInterface todoDataLayerInterface, TodoView view, String command) {
        super(todoDataLayerInterface, view, command);
    }

    @Override
    public void run() {
        if (!ActionHelper.askForYesNoAnswer(Constants.DELETE_ALL_TODOS, view)) {
            return;
        }

        todoDataLayerInterface.deleteAllTodos();
        view.write(Constants.SUCCESSFULLY_DELETED_ALL_STATEMENT);
    }

}
