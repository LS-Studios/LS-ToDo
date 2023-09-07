package de.stubbe.lstodo.extensions

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import java.util.Locale

@Composable
fun Int.getStrResFromLocale(locale: Locale = Locale.getDefault()): String {
    val context = LocalContext.current

    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)

    val localizedContext = context.createConfigurationContext(configuration)

    return localizedContext.getString(this)
}

@Composable
fun Int.getStrRes(): String {
    return stringResource(this)
}

@Composable
fun Int.getPluralStrRes(count: Int?): String {
    return if (count == null) pluralStringResource(this, 1)
    else pluralStringResource(this, count)
}

@Composable
fun Int.getStrArrayRes(): Array<String> {
    return stringArrayResource(this)
}

fun Int.getStrRes(context: Context): String {
    return context.getString(this)
}

@Composable
fun Int.getDrawableRes(): Painter {
    return painterResource(this)
}

@Composable
fun Int.getColorRes(): Color {
    return colorResource(this)
}