package de.check24.dualesstudium.stubbe.actions.model;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.TodoProgram;
import de.check24.dualesstudium.stubbe.actions.service.ActionInterface;
import de.check24.dualesstudium.stubbe.actions.service.functions.*;

public enum Actions {

    CANCEL(Constants.CANCEL_COMMAND, Constants.CANCEL_DESCRIPTION, (view, enteredCommand) -> {
        CancelAction cancelAction = new CancelAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        cancelAction.run();
    }),
    CREATE(Constants.CREATE_COMMAND, Constants.CREATE_DESCRIPTION, (view, enteredCommand) -> {
        CreateAction createAction = new CreateAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        createAction.run();
    }),
    DELETE(Constants.DELETE_COMMAND, Constants.DELETE_DESCRIPTION, (view, enteredCommand) -> {
        DeleteAction deleteAction = new DeleteAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        deleteAction.run();
    }),
    DELETE_ALL(Constants.DELETE_ALL_COMMAND, Constants.DELETE_ALL_DESCRIPTION, (view, enteredCommand) -> {
        DeleteAllAction deleteAllAction = new DeleteAllAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        deleteAllAction.run();
    }),
    SHOW_NOT_DONE(Constants.SHOW_NOT_DONE_COMMAND, Constants.SHOW_NOT_DONE_DESCRIPTION, (view, enteredCommand) -> {
        ShowNotDoneAction showNotDoneAction = new ShowNotDoneAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        showNotDoneAction.run();
    }),
    SHOW_DONE(Constants.SHOW_DONE_COMMAND, Constants.SHOW_DONE_DESCRIPTION, (view, enteredCommand) -> {
        ShowDoneAction showDoneAction = new ShowDoneAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        showDoneAction.run();
    }),
    SHOW_ALL(Constants.SHOW_ALL_COMMAND, Constants.SHOW_ALL_DESCRIPTION, (view, enteredCommand) -> {
        ShowAllAction showAllAction = new ShowAllAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        showAllAction.run();
    }),
    MAKE_DONE(Constants.MAKE_DONE_COMMAND, Constants.MAKE_DONE_DESCRIPTION, (view, enteredCommand) -> {
        MakeDoneAction makeDoneAction = new MakeDoneAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        makeDoneAction.run();
    }),
    MAKE_NOT_DONE(Constants.MAKE_NOT_DONE_COMMAND, Constants.MAKE_NOT_DONE_DESCRIPTION, (view, enteredCommand) -> {
        MakeNotDoneAction makeNotDoneAction = new MakeNotDoneAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        makeNotDoneAction.run();
    }),
    IMPORT_TODOS(Constants.IMPORT_COMMAND, Constants.IMPORT_DESCRIPTION, (view, enteredCommand) -> {
        ImportAction importAction = new ImportAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        importAction.run();
    }),
    EXPORT_TODOS(Constants.EXPORT_COMMAND, Constants.EXPORT_DESCRIPTION, (view, enteredCommand) -> {
        ExportAction exportAction = new ExportAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        exportAction.run();
    }),
    HELP(Constants.HELP_COMMAND, Constants.HELP_DESCRIPTION, (view, enteredCommand) -> {
        HelpAction helpAction = new HelpAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        helpAction.run();
    }),
    QUIT(Constants.QUIT_COMMAND, Constants.QUIT_DESCRIPTION, (view, enteredCommand) -> {
        QuitAction quitAction = new QuitAction(TodoProgram.getGlobalTodoDataLayer(), view, enteredCommand);
        quitAction.run();
    });

    private final String command;
    private final String description;
    private final ActionInterface actionInterface;

    Actions(String command, String description, ActionInterface actionInterface) {
        this.command = command;
        this.description = description;
        this.actionInterface = actionInterface;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public ActionInterface getAction() {
        return actionInterface;
    }

}