package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

public class CancelAction extends AbstractAction {

    public CancelAction(TodoDataLayerInterface todoDataLayerInterface, TodoView view, String command) {
        super(todoDataLayerInterface, view, command);
    }

    @Override
    public void run() {
        view.write(Constants.CANCELING);
    }

}
