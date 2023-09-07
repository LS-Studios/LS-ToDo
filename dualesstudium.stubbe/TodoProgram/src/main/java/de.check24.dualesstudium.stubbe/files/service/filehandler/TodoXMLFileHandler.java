package de.check24.dualesstudium.stubbe.files.service.filehandler;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.files.service.TodoFileHandler;
import de.check24.dualesstudium.stubbe.todo.models.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoXMLFileHandler extends TodoFileHandler {

    @Override
    public String getFileFormat() {
        return Constants.XML_KEYWORD;
    }

    @Override
    public boolean exportTodo(List<Todo> todoList, String path) {
        XStream xStream = new XStream();
        xStream.alias(Constants.TODO_KEYWORD, Todo.class);
        xStream.allowTypes(new Class[] {Todo.class});

        String xmlResult = xStream.toXML(todoList);

        try (FileWriter file = new FileWriter(path)) {
            file.write(xmlResult);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<Todo> importTodo(String path) throws XStreamException {
        XStream xStream = new XStream();
        xStream.alias(Constants.TODO_KEYWORD, Todo.class);
        xStream.allowTypes(new Class[] {Todo.class});

        File pathFile = new File(path);

        var importedTodoList = (List<Todo>) xStream.fromXML(pathFile);

        return importedTodoList == null ? new ArrayList<>() : importedTodoList;
    }

}
