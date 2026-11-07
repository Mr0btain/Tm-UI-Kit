package org.mr0btain.uikit.inputs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.mr0btain.uikit.buttons.TmIconButton
import org.mr0btain.uikit.foundation.TmControlSize
import org.mr0btain.uikit.theme.TmTheme
import org.mr0btain.uikit.resources.Res
import org.mr0btain.uikit.resources.eye
import org.mr0btain.uikit.resources.eye_off

/**
 * Password field with a show/hide button
 * @param showStrength Shows a meter under the field, [onStrengthChange] gets whether it's strong enough
 */
@Composable
fun TmPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = "Password",
    error: String? = null,
    enabled: Boolean = true,
    size: TmControlSize = TmControlSize.MEDIUM,
    showStrength: Boolean = false,
    onStrengthChange: (isStrong: Boolean) -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var visible by rememberSaveable { mutableStateOf(false) }
    val strong = remember(value) { passwordStrength(value) == PasswordStrength.STRONG }

    if (showStrength) {
        LaunchedEffect(strong) { onStrengthChange(strong) }
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(TmTheme.spacing.xs)) {
        TmTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            error = error,
            enabled = enabled,
            size = size,
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = keyboardActions,
            trailing = {
                TmIconButton(
                    icon = if (visible) Res.drawable.eye_off else Res.drawable.eye,
                    contentDescription = if (visible) "Hide password" else "Show password",
                    onClick = { visible = !visible },
                    size = TmControlSize.SMALL
                )
            }
        )

        if (showStrength && value.isNotEmpty()) {
            PasswordStrengthMeter(strong)
        }
    }
}

@Composable
private fun PasswordStrengthMeter(strong: Boolean) {
    val colors = TmTheme.colors
    val tone = if (strong) colors.success else colors.danger

    Column(verticalArrangement = Arrangement.spacedBy(TmTheme.spacing.xs)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(TmTheme.spacing.xs)) {
            repeat(2) { index ->
                val filled = index == 0 || strong
                Box(
                    Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(if (filled) tone.color else colors.hairline, TmTheme.shapes.pill)
                )
            }
        }

        Text(
            text = if (strong) "Strong password"
            else "Use 8 or more characters with upper and lower case letters, a number and a symbol",
            style = TmTheme.typography.caption,
            color = tone.color
        )
    }
}
