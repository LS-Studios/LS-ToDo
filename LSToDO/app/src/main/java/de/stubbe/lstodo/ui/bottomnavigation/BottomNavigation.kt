package de.stubbe.lstodo.ui.bottomnavigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stubbe.lstodo.R
import de.stubbe.lstodo.extensions.getColorRes
import de.stubbe.lstodo.mvp.Presenter
import de.stubbe.lstodo.ui.dialog.CreateEditDialog
import de.stubbe.lstodo.ui.dialog.rememberDialogState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavigation(
    presenter: Presenter,
    sortType: String
) {
    val dialogState = rememberDialogState()

    CreateEditDialog(dialogState = dialogState, onConfirm = { newToDo ->
        presenter.addTodo(newToDo)
    })

    Row(
        verticalAlignment = Alignment.Bottom,
    ) {
        listOf(
            BottomNavigationItem(
                text = "Create",
                icon = Icons.Default.Add,
                onClick = {
                    dialogState.open()
                }
            ),
            BottomNavigationItem(
                text = sortType,
                icon = Icons.Default.Menu,
                onClick = {
                    presenter.onSortTypeChange()
                }
            ),
            BottomNavigationItem(
                text = "Delete done",
                icon = Icons.Default.Delete,
                onClick = {
                    presenter.deleteDoneTodos()
                }
            ),
        ).forEachIndexed { i, bottomBarItem ->
            Column(
                modifier = Modifier
                    .weight(if (i == 1) 2f else 1f)
                    .background(
                        color = ((if (i == 1) R.color.light_main_1 else  R.color.light_main_2).getColorRes()),
                        shape = RoundedCornerShape(
                            topStart = when (i) {
                                0 -> 12.dp
                                1 -> 12.dp
                                else -> 0.dp
                            },
                            topEnd = when (i) {
                                1 -> 12.dp
                                2 -> 12.dp
                                else -> 0.dp
                            },
                        )
                    )
                    .height((if (i == 1) 72 else 56).dp)
                    .padding(8.dp)
                    .clickable {
                        bottomBarItem.onClick()
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = bottomBarItem.icon,
                    contentDescription = null
                )
                Text(
                    text = bottomBarItem.text,
                    color = R.color.light_text.getColorRes(),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}