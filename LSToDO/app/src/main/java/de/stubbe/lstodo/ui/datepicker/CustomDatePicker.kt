package de.stubbe.lstodo.ui.datepicker

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.view.ContextThemeWrapper
import androidx.annotation.RequiresApi
import java.time.LocalDate

class CustomDatePicker(
    val context: Context,
    val style: Int,
    val date: LocalDate,
    val onDateSelected: (LocalDate) -> Unit
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun show(): DatePickerDialog {
        return DatePickerDialog(
            ContextThemeWrapper(
                context,
                style
            ),
            { _, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                onDateSelected(
                    LocalDate.of(
                        selectedYear,
                        selectedMonth,
                        selectedDay
                    )
                )
            },
            date.year,
            date.monthValue,
            date.dayOfMonth,
        ).apply {
            show()
        }
    }
}