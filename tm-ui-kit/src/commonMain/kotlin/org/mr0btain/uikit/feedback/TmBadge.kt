package org.mr0btain.uikit.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mr0btain.uikit.theme.TmTheme
import org.mr0btain.uikit.theme.TmTone

/**
 * Small colored label for a state, like a ticket's status
 * @param solid Fills with the tone's color instead of its faint version, for when it has to stand out
 * @param dot Shows a dot before the text so the color reads even at a glance
 */
@Composable
fun TmBadge(
    text: String,
    tone: TmTone,
    modifier: Modifier = Modifier,
    solid: Boolean = false,
    dot: Boolean = true
) {
    val content = if (solid) tone.onColor else tone.color

    Row(
        modifier = modifier
            .height(22.dp)
            .background(if (solid) tone.color else tone.soft, TmTheme.shapes.chip)
            .padding(horizontal = TmTheme.spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (dot) TmDot(color = content)

        Text(
            text = text,
            style = TmTheme.typography.caption,
            color = content,
            maxLines = 1
        )
    }
}

/** Little round marker in a tone's color, for lists and dropdown options */
@Composable
fun TmDot(
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(modifier.size(7.dp).background(color, TmTheme.shapes.pill))
}
