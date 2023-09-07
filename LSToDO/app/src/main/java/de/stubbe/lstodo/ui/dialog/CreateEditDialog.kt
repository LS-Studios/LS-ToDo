package de.stubbe.lstodo.ui.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import de.stubbe.lstodo.R
import de.stubbe.lstodo.extensions.getColorRes
import de.stubbe.lstodo.extensions.toStringDate
import de.stubbe.lstodo.mvp.Todo
import de.stubbe.lstodo.ui.datepicker.CustomDatePickerField
import de.stubbe.lstodo.ui.standart.CustomTextField

private val dialogRequiredWidth = 360.0.dp
private val dialogMaxHeight = 568.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateEditDialog(
    dialogState: DialogState,
    onConfirm: (Todo) -> Unit,
) {
    if (!dialogState.isOpen.value) return

    Dialog(
        onDismissRequest = {
            dialogState.reset()
            dialogState.close()
        },
    ) {
        Surface(
            modifier = Modifier
                .requiredWidth(dialogRequiredWidth)
                .heightIn(max = dialogMaxHeight),
            color = R.color.light_main_1.getColorRes(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    DialogHeader(
                        text = "Name"
                    )
                    CustomTextField(
                        value = dialogState.name.value,
                        onValueChange = {
                            dialogState.name.value = it
                        },
                    )

                    DialogHeader(
                        text = "Description"
                    )
                    CustomTextField(
                        value = dialogState.description.value,
                        onValueChange = {
                            dialogState.description.value = it
                        },
                    )

                    DialogHeader(
                        text = "Due date"
                    )
                    CustomDatePickerField(
                        date = dialogState.dueDate.value,
                        onDateSelected = {
                            dialogState.dueDate.value = it
                        },
                        valueToText = {
                            it.toStringDate()
                        }
                    )
                }

                DialogButtonBar(
                    buttons = listOf(
                        LSPickerButtonBarButton(
                            text = "Cancel",
                            onClick = {
                                dialogState.reset()
                                dialogState.close()
                            }
                        ),
                        LSPickerButtonBarButton(
                            text = "Confirm",
                            onClick = {
                                onConfirm(dialogState.getItem())
                                dialogState.reset()
                                dialogState.close()
                            }
                        )
                    )
                )
            }
        }
    }
}

@Composable
private fun DialogHeader(text: String) {
    Text(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        text = text,
        color = R.color.light_text.getColorRes(),
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}