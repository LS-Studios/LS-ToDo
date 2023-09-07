package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.actions.service.ActionHelper;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

import java.util.List;

public class ShowNotDoneAction extends AbstractAction {

    public ShowNotDoneAction(TodoDataLayerInterface todoDataLayerInterface, TodoView view, String command) {
        super(todoDataLayerInterface, view, command);
    }

    @Override
    public void run() {
        List<Todo> sorted = command.contains(Constants.SORTED_COMMAND) ? ActionHelper.sortTodo(todoDataLayerInterface.getAllTodos()) : todoDataLayerInterface.getAllTodos();

        for (Todo todo : sorted) {
            if (!todo.isDone()) {
                ActionHelper.showTodo(todo, view);
            }
        }
    }

}
