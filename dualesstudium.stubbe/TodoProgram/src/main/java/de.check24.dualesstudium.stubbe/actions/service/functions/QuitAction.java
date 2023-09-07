package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.TodoProgram;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.service.TodoDatabaseService;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

public class QuitAction extends AbstractAction {

    public QuitAction(TodoDataLayerInterface todoDataLayerInterface, TodoView view, String command) {
        super(todoDataLayerInterface, view, command);
    }

    @Override
    public void run() {
        try {
            TodoDatabaseService dbService = (TodoDatabaseService) todoDataLayerInterface;
            dbService.getDBConnection().closeConnection();
        } catch (Exception e) {
            System.out.println("");
        }

        TodoProgram.isRun.set(false);
        view.write(Constants.QUITING);
    }

}
