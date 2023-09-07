package de.check24.dualesstudium.stubbe.todo.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class TodoHelper {

    private TodoHelper() {}

    public static String getDueDateAsString(LocalDate dueDate) {
        return dueDate == null ? "" : dueDate.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
    }

    public static LocalDate getLocaleDateFromStringDate(String date) throws DateTimeParseException {
        return date.isEmpty() ? null :
                LocalDate.parse(date,
                        DateTimeFormatter.ofPattern("dd.MM.uuuu")
                                .withResolverStyle(ResolverStyle.STRICT)
                );
    }

    public static String getDoneAsString(boolean isDone) {
        return isDone ? "Yes" : "No";
    }

    public static boolean getDoneAsBoolFromString(String isDone) {
        return isDone.equalsIgnoreCase("yes");
    }

}
