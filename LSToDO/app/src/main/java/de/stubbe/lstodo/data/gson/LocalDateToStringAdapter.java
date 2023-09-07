package de.stubbe.lstodo.data.gson;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import de.stubbe.lstodo.data.Constants;

public class LocalDateToStringAdapter implements JsonSerializer<LocalDate> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date == null ? "" : date.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_U)));
    }

}
