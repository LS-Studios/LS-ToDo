package de.stubbe.lstodo.mvp;

import java.util.List;

public interface TodoContract {

    interface View {

        void submitTodoList(List<Todo> todos);

        void setNewSortType(String newSortType);

    }
  
    interface Presenter {

        void reloadTodos();

        void submitTodoList(List<Todo> todos);

        void addTodo(Todo newTodo);

        void deleteDoneTodos();

        void setTodoDone(int id);

        void deleteTodo(int id);

        void onSortTypeChange();

        void onDestroy();
    }
}