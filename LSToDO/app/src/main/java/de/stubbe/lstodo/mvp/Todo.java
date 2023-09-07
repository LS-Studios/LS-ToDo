package de.stubbe.lstodo.mvp;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Todo implements Serializable {

    private int id;
    private String name;
    private String description;
    private LocalDate dueDate;
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
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description != null ? description : "";
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

    @NonNull
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

