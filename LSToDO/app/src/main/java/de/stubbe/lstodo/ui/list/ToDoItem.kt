package de.stubbe.lstodo.ui.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stubbe.lstodo.R
import de.stubbe.lstodo.extensions.getColorRes
import de.stubbe.lstodo.extensions.toStringDate
import de.stubbe.lstodo.mvp.Presenter
import de.stubbe.lstodo.mvp.Todo
import de.stubbe.lstodo.ui.dialog.CreateEditDialog
import de.stubbe.lstodo.ui.dialog.rememberDialogState
import de.stubbe.lstodo.ui.icons.EmptyCircle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ToDoItem(
    presenter: Presenter,
    todo: Todo
) {
    var expanded by remember(todo) { mutableStateOf(false) }
    var done by remember(todo) { mutableStateOf(todo.isDone) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = R.color.light_main_1.getColorRes(),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                expanded = !expanded
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = todo.name,
                color = R.color.light_text.getColorRes(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                style = if (done) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
            )
            Text(
                text = if (todo.dueDate != null) todo.dueDate.toStringDate() else "",
                color = R.color.light_text.getColorRes(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                style = if (done) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
            )
        }

        AnimatedVisibility(expanded) {
            Column {
                Divider(
                    color = R.color.light_divider.getColorRes(),
                    thickness = 4.dp
                )

                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = todo.description,
                    color = R.color.light_text.getColorRes(),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Divider(
                    color = R.color.light_divider.getColorRes(),
                    thickness = 4.dp
                )

                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                color = R.color.light_main_2.getColorRes(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(4.dp)
                            .clickable {
                                done = !done
                                presenter.setTodoDone(todo.id)
                            },
                        imageVector = if (done) Icons.Filled.CheckCircle else Icons.Filled.EmptyCircle,
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                color = R.color.light_main_2.getColorRes(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(4.dp)
                            .clickable {
                                presenter.deleteTodo(todo.id)
                            },
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
        }
    }
}