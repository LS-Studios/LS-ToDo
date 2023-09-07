package de.check24.dualesstudium.stubbe.todo.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class TodoTest {

    @Test
    void todoConstructorTest() {
        int expectedId = 0;
        String expectedName = "Eat";
        String expectedDescription = "Eating food";
        LocalDate expectedDueDate = LocalDate.of(2006, 6, 6);
        boolean expectedDone = false;

        Todo todoToTest = new Todo(expectedId, expectedName, expectedDescription, expectedDueDate, expectedDone);

        assertEquals(expectedId, todoToTest.getId());
        assertEquals(expectedName, todoToTest.getName());
        assertEquals(expectedDescription, todoToTest.getDescription());
        assertEquals(expectedDueDate, todoToTest.getDueDate());
        assertEquals(expectedDone, todoToTest.isDone());
    }

    @Test
    void todoGetterSetterTest() {
        int expectedId = 1;
        String expectedName = "Cook";
        String expectedDescription = "Cooking food";
        LocalDate expectedDueDate = LocalDate.of(2022, 12, 23);
        boolean expectedDone = true;

        Todo todoToTest = new Todo(0, "Test", "Tasty description", LocalDate.of(2001, 1, 1), false);

        todoToTest.setId(expectedId);
        todoToTest.setName(expectedName);
        todoToTest.setDescription(expectedDescription);
        todoToTest.setDueDate(expectedDueDate);
        todoToTest.setDone(expectedDone);

        assertEquals(expectedId, todoToTest.getId());
        assertEquals(expectedName, todoToTest.getName());
        assertEquals(expectedName, todoToTest.getName());
        assertEquals(expectedDescription, todoToTest.getDescription());
        assertEquals(expectedDueDate, todoToTest.getDueDate());
        assertEquals(expectedDone, todoToTest.isDone());
    }

}
