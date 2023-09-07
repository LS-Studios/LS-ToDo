package de.stubbe.lstodo.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stubbe.lstodo.R
import de.stubbe.lstodo.extensions.drawBorder
import de.stubbe.lstodo.extensions.getColorRes


private val pickerButtonBarHeight = 55.dp

@Composable
fun DialogButtonBar(
    buttons: List<LSPickerButtonBarButton>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(pickerButtonBarHeight)
            .background(
                R.color.light_main_2.getColorRes(),
                RoundedCornerShape(
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                )
            ),
    ) {
        buttons.forEachIndexed { i, button ->
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .then(
                        if (i != buttons.size - 1) Modifier.drawBorder(
                            1.dp,
                            R.color.light_text.getColorRes(),
                            right = true
                        ) else Modifier
                    ),
                shape = when (i) {
                    0 -> {
                        RoundedCornerShape(
                            bottomStart = 24.dp
                        )
                    }
                    buttons.size - 1 -> {
                        RoundedCornerShape(
                            bottomEnd = 24.dp
                        )
                    }
                    else -> {
                        RoundedCornerShape(0.dp)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = {
                    button.onClick()
                }
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = button.text,
                    color = R.color.light_text.getColorRes(),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}