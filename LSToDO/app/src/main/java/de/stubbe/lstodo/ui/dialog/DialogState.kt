package de.stubbe.lstodo.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import de.stubbe.lstodo.mvp.Todo
import java.time.LocalDate

data class DialogState(
    val isOpen: MutableState<Boolean>,
    var editedItem: Todo?,
    val name: MutableState<String>,
    val description: MutableState<String>,
    val dueDate: MutableState<LocalDate>,
    val isDone: MutableState<Boolean>,
) {
    fun open() {
        isOpen.value = true
    }

    fun close() {
        isOpen.value = false
    }

    fun getItem(): Todo {
        return Todo().apply {
            name = this@DialogState.name.value
            description = this@DialogState.description.value
            dueDate = this@DialogState.dueDate.value
            isDone = this@DialogState.isDone.value
        }
    }

    fun setItem(item: Todo) {
        name.value = item.name
        description.value = item.description
        dueDate.value = item.dueDate
        isDone.value = item.isDone
    }

    fun reset() {
        name.value = ""
        description.value = ""
        dueDate.value = LocalDate.now()
        isDone.value = false
    }
}

@Composable
fun rememberDialogState(): DialogState {
    return remember {
        DialogState(
            isOpen = mutableStateOf(false),
            editedItem = null,
            name = mutableStateOf(""),
            description = mutableStateOf(""),
            dueDate = mutableStateOf(LocalDate.now()),
            isDone = mutableStateOf(false)
        )
    }
}
