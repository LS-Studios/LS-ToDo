package de.check24.dualesstudium.stubbe.files.service.adapter;

import com.google.gson.*;
import de.check24.dualesstudium.stubbe.todo.models.TodoHelper;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class LocalDateToStringAdapter implements JsonSerializer<LocalDate> {

    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(TodoHelper.getDueDateAsString(date));
    }

}
