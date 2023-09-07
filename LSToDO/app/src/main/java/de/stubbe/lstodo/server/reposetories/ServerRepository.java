package de.stubbe.lstodo.server.reposetories;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import de.stubbe.lstodo.mvp.Todo;
import de.stubbe.lstodo.server.retrofit.ApiUtils;
import de.stubbe.lstodo.server.retrofit.TodoListServerReply;
import de.stubbe.lstodo.server.retrofit.TodoObjectServerReply;
import de.stubbe.lstodo.server.retrofit.TodoServerReply;
import de.stubbe.lstodo.server.retrofit.TodoServerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ServerRepository {

    TodoServerService todoServerService = ApiUtils.getTodoServerService();

    public void getAllTodosFromDatabase(TodoListServerReply serverReply) {
        todoServerService.getTodos().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful()) {
                    serverReply.getTodos(response.body());
                } else {
                    Log.e("Status error", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                serverReply.getTodos(new ArrayList<>());

                t.printStackTrace();
                Log.e("Status error", "Not able to load the todos from the server!");
            }
        });
    }

    public void getTodosSortedByDateFromDatabase(TodoListServerReply serverReply) {
        todoServerService.getTodosSortedByDate().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful()) {
                    serverReply.getTodos(response.body());
                } else {
                    Log.e("Status error", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                serverReply.getTodos(new ArrayList<>());

                t.printStackTrace();
                Log.e("Loading error", "Not able to load the todos from the server!");
            }
        });
    }

    public void getTodosSortedByDoneFromDatabase(TodoListServerReply serverReply) {
        todoServerService.getTodosSortedByDone().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful()) {
                    serverReply.getTodos(response.body());
                } else {
                    Log.e("Status error", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                serverReply.getTodos(new ArrayList<>());

                t.printStackTrace();
                Log.e("Loading error", "Not able to load the todos from the server!");
            }
        });
    }

    public void getTodosSortedByNotDoneFromDatabase(TodoListServerReply serverReply) {
        todoServerService.getTodosSortedByNotDone().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful()) {
                    serverReply.getTodos(response.body());
                } else {
                    Log.e("Status error", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                serverReply.getTodos(new ArrayList<>());

                t.printStackTrace();
                Log.e("Loading error", "Not able to load the todos from the server!");
            }
        });
    }

    public void getTodoFromDatabase(int id, TodoObjectServerReply serverReply) {
        todoServerService.getTodo(id).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()) {
                    serverReply.getTodo(response.body());
                } else {
                    Log.e("Status error", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                serverReply.getTodo(null);

                t.printStackTrace();
                Log.e("Loading error", "Not able to load the todos from the server!");
            }
        });
    }

    public void addTodoToDatabase(Todo todo, TodoServerReply serverReply) {
        todoServerService.addTodo(todo).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                serverReply.then();

                if (!response.isSuccessful()) {
                    Log.e("Status error", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                serverReply.then();

                t.printStackTrace();
                Log.e("Loading error", "Not able to load the todos from the server!");
            }
        });
    }

    public void deleteTodoFromDatabase(int id, TodoServerReply serverReply) {
        todoServerService.deleteTodo(id).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                serverReply.then();

                if (!response.isSuccessful()) {
                    Log.e("Status error", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                serverReply.then();

                t.printStackTrace();
                Log.e("Loading error", "Not able to load the todos from the server!");
            }
        });
    }

    public void deleteDoneTodosFromDatabase(TodoServerReply serverReply) {
        getAllTodosFromDatabase(todos -> {
            for (Todo todo : todos) {
                if (todo.isDone()) {
                    deleteTodoFromDatabase(todo.getId(), serverReply);
                }
            }
        });
    }

    public void changeTodoFromDatabase(int id, boolean newDone, TodoServerReply serverReply) {
        todoServerService.updateTodo(id, newDone).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                serverReply.then();

                if (!response.isSuccessful()) {
                    Log.e("Status error", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                serverReply.then();

                t.printStackTrace();
                Log.e("Loading error", "Not able to load the todos from the server!");
            }
        });
    }

}
