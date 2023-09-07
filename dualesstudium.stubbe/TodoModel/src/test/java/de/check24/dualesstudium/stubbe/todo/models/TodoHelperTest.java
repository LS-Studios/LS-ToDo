package de.check24.dualesstudium.stubbe.todo.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TodoHelperTest {

    @Test
    void getDueDateAsStringTest() {
        String expectedDate = "01.09.2022";

        String dueDateString = TodoHelper.getDueDateAsString(LocalDate.of(2022, 9, 1));

        assertEquals(dueDateString, expectedDate);
    }

    @Test
    void getLocaleDateFromStringDateTest() {
        LocalDate expectedDate = LocalDate.of(2022, 9, 1);

        LocalDate dueDate = TodoHelper.getLocaleDateFromStringDate("01.09.2022");

        assertEquals(dueDate, expectedDate);
    }

    @Test
    void getDoneAsStringTest() {
        String expected = "Yes";

        String isDoneString = TodoHelper.getDoneAsString(true);

        assertEquals(isDoneString, expected);
    }

}
