package de.check24.dualesstudium.stubbe;

public final class Constants {

    private Constants() {}

    public static final int ASK_AGAIN_TIMES = 12;
    public static final String CONFIG_PROPERTIES_FILE_NAME = "./configs/db.properties";
    public static final String DATE_FORMAT_HELP_TEXT = "(Form: dd.mm.yyyy, e.g: 01.01.2001) [If no need press enter]";
    public static final String ENTER_HELP_IF_YOU_NEED = "(Enter \"help\" if you need)";
    public static final String SEPARATOR = "-----------------------------";

    //Errors
    public static final String ANSWER_NOT_VALID = "The entered answer is not valid please enter a valid one!:";
    public static final String CANCEL_BECAUSE_TO_MANY_TIMES_WRONG = "Canceling because you have entered incorrect data too many times ...";
    public static final String COMMAND_CAN_NOT_BE_FOUND = "Entered command cannot be found! " + Constants.ENTER_HELP_IF_YOU_NEED;
    public static final String DUE_DATE_NOT_VALID = "The entered due date is not valid! Please enter a valid one!:\n" + Constants.DATE_FORMAT_HELP_TEXT + ":";
    public static final String FILE_FORMAT_NOT_VALID = "The entered file format is not valid please enter a valid one! (%s):";
    public static final String FILE_FORMAT_NOT_SUPPORTED = "The entered file format is not supported! Please enter a supported one!:";
    public static final String FILE_FORMAT_HAVE_NO_IMPORTER = "The entered file format have no valid importer!";
    public static final String FILE_PATH_NOT_VALID = "The entered filepath is not valid please enter a valid one!:";
    public static final String PATH_NOT_VALID = "The entered path is not valid please enter a valid one!:";

    //Enter questions
    public static final String DELETE_ALL_TODOS = "Do you really want to delete all todos? (yes or no):";
    public static final String DELETE_ALL_TODOS_WHEN_IMPORT = "Do you want to delete all existing todos before importing? (yes or no):";
    public static final String DESCRIPTION_OF_TODO = "Description of the new todo:";
    public static final String DUE_DATE_OF_TODO = "Due date of the new todo.\n" + Constants.DATE_FORMAT_HELP_TEXT + ":";
    public static final String ENTER_COMMAND = "Please enter a command. " + Constants.ENTER_HELP_IF_YOU_NEED;
    public static final String FILE_FORMAT_TO_EXPORT_TO = "File format to export todos to (%s):";
    public static final String NAME_OF_FILE_TO_EXPORT_TO = "Name of file to export todos to:";
    public static final String NAME_OF_TODO = "Name of the new todo:";
    public static final String PATH_TO_EXPORT_TO = "Path to export todos to:";
    public static final String PATH_TO_IMPORT_FROM = "Path to import from:";

    //Model parameters
    public static final String TODO_DESCRIPTION = "description";
    public static final String TODO_DUE_DATE = "dueDate";
    public static final String TODO_ID = "id";
    public static final String TODO_IS_DONE = "isDone";
    public static final String TODO_NAME = "name";
    public static final String TODO_TABLE_NAME = "todos";

    //Action commands
    public static final String CANCEL_COMMAND = ":cancel";
    public static final String CREATE_COMMAND = "create";
    public static final String DELETE_ALL_COMMAND = "delete all";
    public static final String DELETE_COMMAND = "delete";
    public static final String EXPORT_COMMAND = "export";
    public static final String HELP_COMMAND = "help";
    public static final String IMPORT_COMMAND = "import";
    public static final String MAKE_DONE_COMMAND = "make done";
    public static final String MAKE_NOT_DONE_COMMAND = "make not done";
    public static final String QUIT_COMMAND = "quit";
    public static final String SHOW_ALL_COMMAND = "show all";
    public static final String SHOW_DONE_COMMAND = "show done";
    public static final String SHOW_NOT_DONE_COMMAND = "show not done";
    public static final String SORTED_COMMAND = "sorted";

    //Action descriptions
    public static final String CANCEL_DESCRIPTION = "This command will cancel the current command you are in.";
    public static final String CREATE_DESCRIPTION = "This command will create a new todo.";
    public static final String DELETE_ALL_DESCRIPTION = "This command will delete all existing todos.";
    public static final String DELETE_DESCRIPTION = "This command will delete an existing todo.";
    public static final String EXPORT_DESCRIPTION = "This command will export all todos to a file.";
    public static final String HELP_DESCRIPTION = "This command will show all available commands.";
    public static final String IMPORT_DESCRIPTION = "This command will import all todos from a file.";
    public static final String MAKE_DONE_DESCRIPTION = "This command will make todos done.";
    public static final String MAKE_NOT_DONE_DESCRIPTION = "This command will make todos not done anymore.";
    public static final String QUIT_DESCRIPTION = "This command will quit the program.";
    public static final String SHOW_ALL_DESCRIPTION = "This command will print all todos out.";
    public static final String SHOW_DONE_DESCRIPTION = "This command will print all done todos out.";
    public static final String SHOW_NOT_DONE_DESCRIPTION = "This command will print all not done todos out.";

    //Action words
    public static final String CANCELING = "Canceling...";
    public static final String DELETED_STATEMENT = "deleted";
    public static final String DELETE_STATEMENT = "delete";
    public static final String EXPORTED_STATEMENT = "exported";
    public static final String IMPORTED_STATEMENT = "imported";
    public static final String MADE_DONE_STATEMENT = "made done";
    public static final String MADE_NOT_DONE_STATEMENT = "made not done";
    public static final String MAKE_DONE_STATEMENT = "make done";
    public static final String MAKE_NOT_DONE_STATEMENT = "make not done";
    public static final String NOT_SUCCESSFULLY_IMPORTED_EXPORTED_STATEMENT = "It is not possible to %s the todos %s: %s";
    public static final String NO_TODO_WITH_ENTERED_NAME_STATEMENT = "todo with entered name can not be found!\nPlease enter another name.";
    public static final String QUITING = "Program is quiting...";
    public static final String SUCCESSFULLY_ADDED_STATEMENT = "todo \"%s\" successfully added!";
    public static final String SUCCESSFULLY_DELETED_ALL_STATEMENT = "Successfully deleted all todos!";
    public static final String SUCCESSFULLY_IMPORTED_EXPORTED_STATEMENT = "The todos successfully %s %s: %s";
    public static final String SUCCESSFULLY_STATEMENT = "todo \"%s\" successfully %s!";

    //Keywords
    public static final String FROM_KEYWORD = "from";
    public static final String JSON_KEYWORD = "json";
    public static final String TODO_KEYWORD = "todo";
    public static final String TO_KEYWORD = "to";
    public static final String XML_KEYWORD = "xml";

}
