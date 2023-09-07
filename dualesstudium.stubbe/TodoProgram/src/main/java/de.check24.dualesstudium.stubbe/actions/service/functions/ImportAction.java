package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.actions.model.Actions;
import de.check24.dualesstudium.stubbe.actions.service.ActionHelper;
import de.check24.dualesstudium.stubbe.files.service.FileHelper;
import de.check24.dualesstudium.stubbe.files.service.TodoFileHandler;
import de.check24.dualesstudium.stubbe.files.service.filehandler.TodoJsonFileHandler;
import de.check24.dualesstudium.stubbe.files.service.filehandler.TodoXMLFileHandler;
import de.check24.dualesstudium.stubbe.todo.models.Todo;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

import java.util.ArrayList;
import java.util.List;

public class ImportAction extends AbstractAction {

    public ImportAction(TodoDataLayerInterface todoDataLayerInterface, TodoView view, String command) {
        super(todoDataLayerInterface, view, command);
    }

    @Override
    public void run() {
        view.write(Constants.PATH_TO_IMPORT_FROM);

        String enteredFilePath = view.read();

        int alreadyAskedTimes1 = 0;

        while (alreadyAskedTimes1 < Constants.ASK_AGAIN_TIMES && !FileHelper.checkFileValidity(enteredFilePath)) {
            alreadyAskedTimes1++;

            view.write(Constants.FILE_PATH_NOT_VALID);

            enteredFilePath = view.read();

            if (ActionHelper.cancelCheck(enteredFilePath)) {
                Actions.CANCEL.getAction().action(view, command);
                return;
            }
        }

        if (alreadyAskedTimes1 >= Constants.ASK_AGAIN_TIMES) {
            view.write(Constants.CANCEL_BECAUSE_TO_MANY_TIMES_WRONG);
            return;
        }

        List<Todo> importedTodos;

        List<TodoFileHandler> fileHandlers = new ArrayList<>(FileHelper.getTodoFileHandlers("plugins"));
        fileHandlers.add(new TodoJsonFileHandler());
        fileHandlers.add(new TodoXMLFileHandler());

        int alreadyAskedTimes2 = 0;

        while (alreadyAskedTimes2 < Constants.ASK_AGAIN_TIMES && !FileHelper.checkFileFormatValidity(enteredFilePath, fileHandlers)) {
            view.write(Constants.FILE_FORMAT_NOT_SUPPORTED);

            alreadyAskedTimes2++;
        }

        if (alreadyAskedTimes2 >= Constants.ASK_AGAIN_TIMES) {
            view.write(Constants.CANCEL_BECAUSE_TO_MANY_TIMES_WRONG);
            return;
        }

        importedTodos = getImportedTodoIfPossible(fileHandlers, enteredFilePath);

        if (importedTodos == null) {
            view.write(Constants.FILE_FORMAT_HAVE_NO_IMPORTER);
            return;
        }

        if (ActionHelper.askForYesNoAnswer(Constants.DELETE_ALL_TODOS_WHEN_IMPORT, view)) {
            todoDataLayerInterface.deleteAllTodos();
        }

        for (Todo todo : importedTodos) {
            todoDataLayerInterface.addTodo(todo);
        }

        view.write(String.format(Constants.SUCCESSFULLY_IMPORTED_EXPORTED_STATEMENT, Constants.IMPORTED_STATEMENT, Constants.FROM_KEYWORD, enteredFilePath));
    }

    private List<Todo> getImportedTodoIfPossible(List<TodoFileHandler> fileHandlers, String enteredFilePath) {
        for (TodoFileHandler fileHandler : fileHandlers) {
            String fileFormat = fileHandler.getFileFormat();

            String[] split = enteredFilePath.split("\\.");

            if (split[split.length - 1].equals(fileFormat)) {
                try {
                    return fileHandler.importTodo(enteredFilePath);
                } catch (Exception e) {
                    view.write(String.format(Constants.NOT_SUCCESSFULLY_IMPORTED_EXPORTED_STATEMENT, Constants.IMPORT_COMMAND, Constants.FROM_KEYWORD, enteredFilePath));
                    return null;
                }
            }
        }

        return null;
    }

}
