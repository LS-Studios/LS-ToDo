package de.stubbe.lstodo.data.gson;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import de.stubbe.lstodo.data.Constants;

public class StringToLocalDateAdapter implements JsonDeserializer<LocalDate> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsJsonPrimitive().getAsString().isEmpty() ? null :
                LocalDate.parse(json.getAsJsonPrimitive().getAsString(),
                        DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_U)
                                .withResolverStyle(ResolverStyle.STRICT)
                );
    }

}
