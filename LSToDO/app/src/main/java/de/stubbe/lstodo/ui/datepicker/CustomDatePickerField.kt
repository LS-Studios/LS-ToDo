package de.stubbe.lstodo.ui.datepicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stubbe.lstodo.R
import de.stubbe.lstodo.extensions.getColorRes
import de.stubbe.lstodo.extensions.lsField
import java.time.LocalDate

@Composable
fun CustomDatePickerField(
    modifier: Modifier = Modifier,
    date: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    valueToText: (LocalDate) -> String
) {
    val context = LocalContext.current

    val datePickerStyle = R.style.LightDatePickerDialogTheme

    Box(modifier = modifier
        .lsField()
        .clickable {
            CustomDatePicker(
                context = context,
                style = datePickerStyle,
                date = date,
                onDateSelected = { selectedDate ->
                    onDateSelected(selectedDate)
                }
            ).show()
        },
    contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            text = valueToText(date),
            color = R.color.light_text.getColorRes(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}