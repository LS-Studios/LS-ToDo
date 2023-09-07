package de.stubbe.lstodo.mvp;

import android.os.Build;

import androidx.annotation.RequiresApi;

//import com.pusher.client.Pusher;
//import com.pusher.client.PusherOptions;
//import com.pusher.client.channel.Channel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

import de.stubbe.lstodo.data.Constants;
import de.stubbe.lstodo.server.reposetories.ServerRepository;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Presenter implements TodoContract.Presenter {

    private TodoContract.View view;

    private List<Todo> todoList;

    private static int currentSortType = 0;

    private ServerRepository repository = new ServerRepository();

//    private Pusher pusher;
//    private Channel channel;

    public Presenter(TodoContract.View view) {
        this.view = view;
//        PusherOptions options = new PusherOptions();
//        options.setCluster("eu");

//        pusher = new Pusher("eea4fed65a850b489d4c", options);
//        pusher.connect();
//
//        channel = pusher.subscribe("todos");
//
//        channel.bind("update",data -> reloadTodos());
}

    @Override
    public void reloadTodos() {
        String sortBy = "Sort by ";

        switch(currentSortType){
            case 0:
                repository.getAllTodosFromDatabase(todos ->{
                    view.setNewSortType(sortBy+"nothing");
                    view.submitTodoList(todos);
                    todoList = todos;
                });
                break;
            case 1:
                repository.getTodosSortedByDateFromDatabase(todos ->{
                    view.setNewSortType(sortBy+"date");
                    view.submitTodoList(todos);
                    todoList = todos;
                });
                break;
            case 2:
                repository.getTodosSortedByDoneFromDatabase(todos ->{
                    view.setNewSortType(sortBy+"done");
                    view.submitTodoList(todos);
                    todoList = todos;
                });
                break;
            case 3:
                repository.getTodosSortedByNotDoneFromDatabase(todos ->{
                    view.setNewSortType(sortBy+"not done");
                    view.submitTodoList(todos);
                    todoList = todos;
                });
                break;
        }
    }

    @Override
    public void submitTodoList(List<Todo> todos) {
        this.view.submitTodoList(todos);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addTodo(Todo newTodo) {
        todoList.add(newTodo);
        submitTodoList(todoList);
        repository.addTodoToDatabase(newTodo, this::reloadTodos);
    }

    @Override
    public void deleteDoneTodos() {
        for (int i = 0; i < todoList.size(); i++) {
            if (todoList.get(i).isDone()) {
                todoList.remove(i);
                submitTodoList(todoList);
                repository.deleteTodoFromDatabase(todoList.get(i).getId(), () -> {});
                break;
            }
        }
    }

    @Override
    public void setTodoDone(int id) {
        repository.getTodoFromDatabase(id, todo -> {
            if (todo != null) {
                todoList.stream().filter(t -> t.getId() == id).findFirst().ifPresent(t -> t.setDone(!t.isDone()));
                view.submitTodoList(todoList);
                repository.changeTodoFromDatabase(id, !todo.isDone(), this::reloadTodos);
            } else {
                todoList.stream().filter(t -> t.getId() == id).findFirst().ifPresent(t -> todoList.remove(t));
                view.submitTodoList(todoList);
            }
        });
    }

    @Override
    public void deleteTodo(int id) {
        repository.deleteTodoFromDatabase(id, () -> {});
        todoList.stream().filter(todo -> todo.getId() == id).findFirst().ifPresent(todo -> todoList.remove(todo));
        view.submitTodoList(todoList);
    }

    @Override
    public void onSortTypeChange() {
        int newSortType = currentSortType + 1;

        if (newSortType > 3) {
            newSortType = 0;
        }

        currentSortType = newSortType;

        reloadTodos();
    }

    @Override
    public void onDestroy() {
        view = null;
    }

}
