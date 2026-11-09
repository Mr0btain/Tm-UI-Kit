package org.mr0btain.uikit.files

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.mr0btain.uikit.buttons.TmButton
import org.mr0btain.uikit.buttons.TmButtonVariant
import org.mr0btain.uikit.buttons.TmIconButton
import org.mr0btain.uikit.inputs.TmTextField
import org.mr0btain.uikit.resources.Res
import org.mr0btain.uikit.resources.x
import org.mr0btain.uikit.theme.TmTheme

/**
 * A chosen file or folder: its path (read only, since it's picked rather than typed) with Browse beside it. The app
 * opens its own picker in [onBrowse], so the kit doesn't depend on one
 * @param path Null or empty shows [placeholder], like what happens with nothing chosen
 * @param onClear Shows an × in the field while a path is set, for going back to none
 * @param clearDescription What the × does, for screen readers
 */
@Composable
fun TmPathField(
    path: String?,
    onBrowse: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    supportingText: String? = null,
    error: String? = null,
    onClear: (() -> Unit)? = null,
    clearDescription: String = "Clear",
    browseText: String = "Browse",
    enabled: Boolean = true
) {
    val colors = TmTheme.colors
    val spacing = TmTheme.spacing
    val hasPath = !path.isNullOrEmpty()

    // Label and help drawn here rather than by the field, so Browse lines up with the field itself
    Column(modifier, verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
        if (label != null) {
            Text(text = label, style = TmTheme.typography.caption, color = if (error != null) colors.danger.color else colors.textMuted)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm), verticalAlignment = Alignment.CenterVertically) {
            TmTextField(
                value = path.orEmpty(),
                onValueChange = {},
                readOnly = true,
                placeholder = placeholder,
                // Turns the border red; the message itself is shown below
                error = error?.let { "" },
                enabled = enabled,
                modifier = Modifier.weight(1f),
                trailing = if (hasPath && onClear != null && enabled) {
                    { TmIconButton(icon = Res.drawable.x, contentDescription = clearDescription, onClick = onClear) }
                } else null
            )
            TmButton(text = browseText, onClick = onBrowse, variant = TmButtonVariant.SECONDARY, enabled = enabled)
        }
        val message = (error ?: supportingText)?.takeIf { it.isNotBlank() }
        if (message != null) {
            Text(text = message, style = TmTheme.typography.caption, color = if (error != null) colors.danger.color else colors.textMuted)
        }
    }
}
