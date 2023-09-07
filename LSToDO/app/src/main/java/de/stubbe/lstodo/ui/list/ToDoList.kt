package de.stubbe.lstodo.ui.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.stubbe.lstodo.mvp.Presenter
import de.stubbe.lstodo.mvp.Todo
import de.stubbe.lstodo.mvp.TodoContract
import de.stubbe.lstodo.ui.dialog.CreateEditDialog
import de.stubbe.lstodo.ui.dialog.rememberDialogState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ToDoList(
    todoList: List<Todo>,
    presenter: Presenter
) {
    LaunchedEffect(Unit) {
        presenter.reloadTodos()
    }

    LazyColumn(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxSize()
    ) {
        items(todoList) { todo ->
            ToDoItem(presenter, todo)
        }
    }
}