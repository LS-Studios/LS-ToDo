package de.stubbe.lstodo.server.retrofit;

import java.util.List;

import de.stubbe.lstodo.mvp.Todo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TodoServerService {

    @Headers("Accept: application/json")
    @GET("/todos")
    Call<List<Todo>> getTodos();

    @Headers("Accept: application/json")
    @GET("/todos/date")
    Call<List<Todo>> getTodosSortedByDate();

    @Headers("Accept: application/json")
    @GET("/todos/done")
    Call<List<Todo>> getTodosSortedByDone();

    @Headers("Accept: application/json")
    @GET("/todos/not-done")
    Call<List<Todo>> getTodosSortedByNotDone();

    @Headers("Accept: application/json")
    @GET("/todos/{id}")
    Call<Todo> getTodo(@Path("id") int id);

    @Headers("Accept: application/json")
    @POST("/todos")
    Call<Todo> addTodo(@Body Todo todo);

    @Headers("Accept: application/json")
    @DELETE("/todos/{id}")
    Call<Todo> deleteTodo(@Path("id") int id);

    @Headers("Accept: application/json")
    @PATCH("/todos/{id}")
    Call<Todo> updateTodo(@Path("id") int id, @Query("done") boolean newIsDone);

}
