package de.check24.dualesstudium.stubbe.files.service.adapter;

import com.google.gson.*;
import de.check24.dualesstudium.stubbe.todo.models.TodoHelper;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class StringToLocalDateAdapter implements JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return TodoHelper.getLocaleDateFromStringDate(json.getAsJsonPrimitive().getAsString());
    }

}
