package de.check24.dualesstudium.stubbe.files.service.filehandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.files.service.TodoFileHandler;
import de.check24.dualesstudium.stubbe.files.service.adapter.LocalDateToStringAdapter;
import de.check24.dualesstudium.stubbe.files.service.adapter.StringToLocalDateAdapter;
import de.check24.dualesstudium.stubbe.todo.models.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TodoJsonFileHandler extends TodoFileHandler {

    @Override
    public String getFileFormat() {
        return Constants.JSON_KEYWORD;
    }

    @Override
    public boolean exportTodo(List<Todo> todoList, String path) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateToStringAdapter())
                .create();

        String jsonResult = gson.toJson(todoList);

        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonResult);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<Todo> importTodo(String path) throws IOException, JsonParseException, NoSuchElementException, IllegalStateException {
        StringBuilder jsonText = new StringBuilder();
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            jsonText.append(data);
        }
        myReader.close();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new StringToLocalDateAdapter())
                .create();

        Type todoListType = new TypeToken<ArrayList<Todo>>(){}.getType();
        List<Todo> importedTodoList = gson.fromJson(jsonText.toString(), todoListType);

        return importedTodoList == null ? new ArrayList<>() : importedTodoList;
    }

}
