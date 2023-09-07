package de.stubbe.lstodo.extensions

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import de.stubbe.lstodo.R
import de.stubbe.lstodo.data.Constants
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.concurrent.TimeUnit


@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDate(): LocalDate =
    DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT).parse(this, LocalDate::from)

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDefaultStringDate(): String =
    this.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT))

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toStringDate(): String =
    this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))