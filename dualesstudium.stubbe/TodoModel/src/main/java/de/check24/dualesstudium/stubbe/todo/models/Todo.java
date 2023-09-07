package de.check24.dualesstudium.stubbe.todo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Todo implements Serializable {
    @Expose
    @Schema(type="integer", example = "0")
    @JsonView(TodoIdView.Create.class)
    private int id;
    @Schema(type="string", example = "Test a todo")
    @JsonView(TodoNoIdView.Create.class)
    private String name;
    @Schema(type="string", example = "A todo to test")
    @JsonView(TodoNoIdView.Create.class)
    private String description;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Schema(type="string", example = "22.09.2022", pattern = "dd.MM.yyyy")
    @JsonView(TodoNoIdView.Create.class)
    private LocalDate dueDate;
    @Schema(type="boolean", example = "true")
    @JsonView(TodoNoIdView.Create.class)
    private boolean done;

    public Todo() {
    }

    public Todo(int id, String name, String description, LocalDate dueDate, Boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Objects.requireNonNullElse(name, "");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Objects.requireNonNullElse(description, "");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean checked) {
        done = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return id == todo.id && done == todo.done && Objects.equals(name, todo.name) && Objects.equals(description, todo.description) && Objects.equals(dueDate, todo.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, dueDate, done);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", done=" + done +
                '}';
    }

}

