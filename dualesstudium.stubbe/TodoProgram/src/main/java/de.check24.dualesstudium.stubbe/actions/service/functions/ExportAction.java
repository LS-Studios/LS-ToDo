package de.check24.dualesstudium.stubbe.actions.service.functions;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.actions.model.Actions;
import de.check24.dualesstudium.stubbe.actions.service.ActionHelper;
import de.check24.dualesstudium.stubbe.files.service.FileHelper;
import de.check24.dualesstudium.stubbe.files.service.TodoFileHandler;
import de.check24.dualesstudium.stubbe.files.service.filehandler.TodoJsonFileHandler;
import de.check24.dualesstudium.stubbe.files.service.filehandler.TodoXMLFileHandler;
import de.check24.dualesstudium.stubbe.todo.service.TodoDataLayerInterface;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExportAction extends AbstractAction {

    public ExportAction(TodoDataLayerInterface todoDataLayerInterface, TodoView view, String command) {
        super(todoDataLayerInterface, view, command);
    }

    @Override
    public void run() {
        //Ask for path
        view.write(Constants.PATH_TO_EXPORT_TO);

        String enteredPath = askForPath();

        if (enteredPath == null || ActionHelper.cancelCheck(enteredPath)) {
            Actions.CANCEL.getAction().action(view, command);
            return;
        }

        if (directFilePathIsGiven(enteredPath)) {
            doExport(enteredPath);
            return;
        }

        //Ask for name
        view.write(Constants.NAME_OF_FILE_TO_EXPORT_TO);

        String enteredName = view.read();

        if (ActionHelper.cancelCheck(enteredName)) {
            Actions.CANCEL.getAction().action(view, command);
            return;
        }

        enteredPath = createNotExistingDirectory(enteredPath + enteredName, true);

        if (directFilePathIsGiven(enteredPath)) {
            doExport(enteredPath);
            return;
        }

        //Ask for file format
        view.write(String.format(Constants.FILE_FORMAT_TO_EXPORT_TO, getFileFormatListString()));

        String enteredFileFormat = askForFileFormat();

        if (enteredFileFormat == null || ActionHelper.cancelCheck(enteredFileFormat)) {
            Actions.CANCEL.getAction().action(view, command);
            return;
        }

        doExport(enteredPath + "." + enteredFileFormat);
    }

    private void doExport(String path) {
        boolean result = false;

        String[] split = path.split("\\.");

        List<TodoFileHandler> fileHandlers = new ArrayList<>(FileHelper.getTodoFileHandlers("plugins"));
        fileHandlers.add(new TodoJsonFileHandler());
        fileHandlers.add(new TodoXMLFileHandler());

        for (TodoFileHandler fileHandler : fileHandlers) {
            String fileFormat = fileHandler.getFileFormat();

            if (split[split.length - 1].equals(fileFormat)) {
                result = fileHandler.exportTodo(todoDataLayerInterface.getAllTodos(), path);
            }
        }

        if (result) {
            view.write(String.format(Constants.SUCCESSFULLY_IMPORTED_EXPORTED_STATEMENT, Constants.EXPORTED_STATEMENT, Constants.TO_KEYWORD, path));
        } else {
            view.write(String.format(Constants.NOT_SUCCESSFULLY_IMPORTED_EXPORTED_STATEMENT, Constants.EXPORT_COMMAND, Constants.TO_KEYWORD, path));
        }
    }

    private String askForPath() {
        String enteredPath = view.read();

        if (ActionHelper.cancelCheck(enteredPath)) {
            return null;
        }

        if (enteredPath.isEmpty()) {
            return "./";
        }

        int alreadyAskedTimes = 0;

        if (enteredPath.charAt(0) != '/' && enteredPath.charAt(0) != '.') {
            enteredPath = "./" + enteredPath;
        }

        enteredPath = createNotExistingDirectory(enteredPath, false);

        while (alreadyAskedTimes < Constants.ASK_AGAIN_TIMES && !FileHelper.checkPathValidity(enteredPath)
                && !ActionHelper.cancelCheck(enteredPath)) {
            alreadyAskedTimes++;

            view.write(Constants.PATH_NOT_VALID);

            enteredPath = view.read();

            if (ActionHelper.cancelCheck(enteredPath)) {
                return null;
            }
        }

        if (alreadyAskedTimes >= Constants.ASK_AGAIN_TIMES) {
            view.write(Constants.CANCEL_BECAUSE_TO_MANY_TIMES_WRONG);
            return null;
        }

        return enteredPath;
    }

    private String askForFileFormat() {
        String enteredFileFormat = view.read();

        if (ActionHelper.cancelCheck(enteredFileFormat)) {
            return null;
        }

        int alreadyAskedTimes = 0;

        while (alreadyAskedTimes < Constants.ASK_AGAIN_TIMES && getFileFormatList().stream().noneMatch(enteredFileFormat::equalsIgnoreCase)
                && !ActionHelper.cancelCheck(enteredFileFormat)) {
            alreadyAskedTimes++;

            view.write(String.format(Constants.FILE_FORMAT_NOT_VALID, getFileFormatListString()));

            enteredFileFormat = view.read();

            if (ActionHelper.cancelCheck(enteredFileFormat)) {
                return null;
            }
        }

        if (alreadyAskedTimes >= Constants.ASK_AGAIN_TIMES) {
            view.write(Constants.CANCEL_BECAUSE_TO_MANY_TIMES_WRONG);
            return null;
        }

        return enteredFileFormat;
    }

    private List<String> getFileFormatList() {
        List<String> fileFormats = new ArrayList<>();

        List<TodoFileHandler> fileHandlers = new ArrayList<>(FileHelper.getTodoFileHandlers("plugins"));
        fileHandlers.add(new TodoJsonFileHandler());
        fileHandlers.add(new TodoXMLFileHandler());

        for (TodoFileHandler fileHandler : fileHandlers) {
            String fileFormat = fileHandler.getFileFormat();

            fileFormats.add(fileFormat);
        }

        return fileFormats;
    }

    private String getFileFormatListString() {
        String listString = getFileFormatList().toString();
        listString = listString.replace("[", "").replaceFirst("]", "");

        String[] split = listString.split(",");

        StringBuilder displayString = new StringBuilder();

        for (int i = 0; i < split.length; i++) {
            if (i == split.length-2) {
                displayString.append(split[i]).append(" or");
            }
            else if (i == split.length-1) {
                displayString.append(split[i]);
            }
            else {
                displayString.append(split[i]).append(",");
            }
        }

        return displayString.toString();
    }

    private String createNotExistingDirectory(String path, Boolean isNamePath) {
        String editPath = path;
        String directoryPath = null;

        if (!directFilePathIsGiven(editPath)) {
            if (isNamePath) {
                directoryPath = editPath;
                if (editPath.charAt(editPath.length()-1) == '/') {
                    StringBuilder stringBuilder = new StringBuilder(editPath);
                    stringBuilder.deleteCharAt(editPath.length() - 1);
                    directoryPath = stringBuilder.toString();
                    editPath = directoryPath;
                }
                String regX = "([^/]+$)";
                directoryPath = directoryPath.replaceFirst(regX, "");
            } else if (editPath.length() > 0 && editPath.charAt(editPath.length()-1) != '/') {
                directoryPath = String.format("%s/", editPath);
                editPath = directoryPath;
            } else {
                directoryPath = editPath;
            }
        } else {
            directoryPath = editPath;
            String regX = "([^/]+$)";
            directoryPath = directoryPath.replaceFirst(regX, "");
        }

        File file = new File(directoryPath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return editPath;
    }

    private boolean directFilePathIsGiven(String path) {
        String[] pathSplit = path.split("/");

        if (pathSplit.length == 0) return false;

        for (String fileFormat : getFileFormatList()) {
            if (pathSplit[pathSplit.length-1].toLowerCase().contains("." + fileFormat)) {
                return true;
            }
        }

        return false;
    }

}