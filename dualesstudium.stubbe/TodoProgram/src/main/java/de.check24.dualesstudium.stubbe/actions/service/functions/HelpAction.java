package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.actions.model.Actions;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

public class HelpAction extends AbstractAction {

    public HelpAction(TodoDataLayerInterface todoDataLayerInterface, TodoView view, String command) {
        super(todoDataLayerInterface, view, command);
    }

    @Override
    public void run() {
        for (Actions action : Actions.values()) {
            view.write(Constants.SEPARATOR);
            view.write(String.format("Command:     \"%s\"%nDescription: %s%n", action.getCommand(), action.getDescription()));
        }
    }

}
