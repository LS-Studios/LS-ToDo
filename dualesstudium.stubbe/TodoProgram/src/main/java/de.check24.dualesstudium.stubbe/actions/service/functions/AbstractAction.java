package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

public abstract class AbstractAction {

    protected TodoDataLayerInterface todoDataLayerInterface;
    protected TodoView view;
    protected final String command;

    protected AbstractAction(TodoDataLayerInterface todoDataLayerInterface,
                             TodoView view,
                             String command) {
        this.todoDataLayerInterface = todoDataLayerInterface;
        this.view = view;
        this.command = command;
    }

    abstract void run();

}
