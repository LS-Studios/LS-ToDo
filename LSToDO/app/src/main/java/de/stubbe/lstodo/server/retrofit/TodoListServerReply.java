package de.stubbe.lstodo.server.retrofit;

import java.util.List;

import de.stubbe.lstodo.mvp.Todo;

public interface TodoListServerReply {

    void getTodos(List<Todo> todos);

}
