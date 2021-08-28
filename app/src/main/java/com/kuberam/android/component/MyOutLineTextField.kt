package com.kuberam.android.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kuberam.android.R
import com.kuberam.android.utils.focusOutlineBorderColor
import com.kuberam.android.utils.textNormalColor
import com.kuberam.android.utils.unfocusOutlineBorderColor

@Composable
fun MyOutLineTextField(
    value: String,
    isDarkTheme: Boolean,
    focusManager: FocusManager? = null,
    onChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIconImageVector: ImageVector? = Icons.Default.Add,
    leadingIconPainterResource: Painter? = painterResource(R.drawable.ic_baseline_add_circle_outline_24),
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onChange.invoke(it)
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        leadingIcon = {
            leadingIconImageVector?.let {
                Icon(imageVector = it, contentDescription = null)
            } ?: leadingIconPainterResource?.let {
                Icon(painter = it, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager?.clearFocus() }
        ),
        singleLine = isSingleLine,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = textNormalColor(
                isDarkTheme
            ),
            unfocusedBorderColor = unfocusOutlineBorderColor(isDarkTheme),
            focusedBorderColor = focusOutlineBorderColor(isDarkTheme)
        ),
        shape = RoundedCornerShape(12.dp),
        textStyle = MaterialTheme.typography.h2
    )
}
