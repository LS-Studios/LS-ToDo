package de.stubbe.lstodo.ui.standart

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stubbe.lstodo.R
import de.stubbe.lstodo.extensions.getColorRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    textFieldInputType: LSTextFieldInputType = LSTextFieldInputType.TEXT,
    height: Dp = 35.dp,
    useIndicator: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textAlign: TextAlign = TextAlign.Center,
    contentAlign: Alignment = Alignment.Center,
    textStyle: TextStyle = LocalTextStyle.current.copy(
        fontSize = 18.sp,
        textAlign = textAlign,
        color = R.color.light_text.getColorRes()
    ),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = when(textFieldInputType){
        LSTextFieldInputType.TEXT -> KeyboardOptions.Default
        LSTextFieldInputType.EMAIL -> KeyboardOptions(keyboardType = KeyboardType.Email)
        else -> KeyboardOptions(keyboardType = KeyboardType.Number)
    },
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    placeholder: String? = null,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    shape: Shape = RoundedCornerShape(4.dp),
    focusRequester: FocusRequester? = null,
) {
    val intPattern = remember { Regex("^\\d+\$") }
    val doublePattern = remember { Regex("^-?(\\d+(?:[.,]\\d+)?\\.?\\,?)?\$") }

    BasicTextField(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .background(backgroundColor, shape)
            .then(
                if (focusRequester != null)
                    Modifier.focusRequester(focusRequester)
                else
                    Modifier
            ),
        value = value,
        onValueChange = { changedValue ->
            when(textFieldInputType) {
                LSTextFieldInputType.INT -> {
                    if (changedValue.isEmpty() || changedValue.matches(intPattern))
                        onValueChange(changedValue)
                }
                LSTextFieldInputType.DOUBLE -> {
                    if (changedValue.isEmpty() || changedValue.matches(doublePattern))
                        onValueChange(changedValue.replace(",", "."))
                }
                else -> onValueChange(changedValue)
            }
        },
        singleLine = singleLine,
        maxLines = maxLines,
        enabled = enabled,
        readOnly = readOnly,
        interactionSource = interactionSource,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onTextLayout = onTextLayout,
        cursorBrush = SolidColor(textStyle.color),
        decorationBox = { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                innerTextField = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = contentAlign
                    ) {
                        innerTextField()
                    }
                },
                enabled = enabled,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = textStyle.color,
                    errorCursorColor = textStyle.color,
                    containerColor = Color.Transparent,
                    disabledTextColor = textStyle.color,
                    focusedIndicatorColor = if (useIndicator) R.color.light_text.getColorRes() else Color.Transparent,
                    unfocusedIndicatorColor = if (useIndicator) R.color.light_text.getColorRes() else Color.Transparent,
                    disabledIndicatorColor = if (useIndicator) R.color.light_text.getColorRes() else Color.Transparent
                ),
                singleLine = singleLine,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = 0.dp,
                    bottom = 0.dp
                ),
                placeholder = {
                    if (value.isEmpty() && placeholder != null) {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .alpha(0.5f)
                                    .fillMaxWidth(),
                                text = placeholder,
                                color = R.color.light_text.getColorRes(),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Visible
                            )
                        }
                    }
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }
    )
}


