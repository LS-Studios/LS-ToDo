package de.stubbe.lstodo.extensions

import android.content.Context
import android.graphics.BlurMaskFilter
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stubbe.lstodo.R
import de.stubbe.lstodo.data.extensions.ButtonState
import kotlin.math.abs

fun Modifier.drawBorder(strokeWidth: Dp,
                        color: Color,
                        top: Boolean = false,
                        bottom: Boolean = false,
                        left: Boolean = false,
                        right: Boolean = false
) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx/2

            if (top)
                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = width , y = 0f),
                    strokeWidth = strokeWidthPx
                )
            if (left)
                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = 0f, y = height),
                    strokeWidth = strokeWidthPx
                )
            if (right)
                drawLine(
                    color = color,
                    start = Offset(x = width, y = 0f),
                    end = Offset(x = width, y = height),
                    strokeWidth = strokeWidthPx
                )
            if (bottom)
                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = height),
                    end = Offset(x = width, y = height),
                    strokeWidth = strokeWidthPx
                )
        }
    }
)

fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + topPixel
            val bottomPixel = size.height + leftPixel

            canvas.drawRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                paint = paint,
            )
        }
    }
)

fun Modifier.bounceClick(bounce: Float = 0.9f) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.IDLE) }
    val scale by animateFloatAsState(
        if (buttonState == ButtonState.PRESSED) bounce else 1f,
        label = "bounce",
        )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.PRESSED) {
                    waitForUpOrCancellation()
                    ButtonState.IDLE
                } else {
                    awaitFirstDown(false)
                    ButtonState.PRESSED
                }
            }
        }
}

fun Modifier.lsField() = composed {
    this
        .background(
            R.color.light_main_2.getColorRes(),
            RoundedCornerShape(8.dp)
        )
        .padding(8.dp)
        .fillMaxWidth()
}