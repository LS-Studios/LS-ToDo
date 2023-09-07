package de.check24.dualesstudium.stubbe.actions.service;

import de.check24.dualesstudium.stubbe.todo.view.TodoView;

public interface ActionInterface {

    void action(TodoView view, String command);

}