package org.mr0btain.uikit.files

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mr0btain.uikit.theme.TmTheme

/**
 * Takes files and folders dragged in from the file manager onto [content]. While something is dragged over, the
 * area is outlined in the accent color with [hint] over it. Only desktop has file drag and drop; elsewhere this is
 * just [content], so screens can use it everywhere and offer a picker too
 * @param onDrop Local paths of what was dropped, never empty. Called only for drops of files
 */
@Composable
expect fun TmDropZone(
    onDrop: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    hint: String = "Drop to add",
    content: @Composable BoxScope.() -> Unit
)

/** What a drop zone shows over its content while files are dragged over it */
@Composable
internal fun BoxScope.TmDropHighlight(hint: String) {
    val shape = TmTheme.shapes.panel
    Box(
        modifier = Modifier
            .matchParentSize()
            // Solid first so what's underneath doesn't show through the tint
            .background(TmTheme.colors.surfaceRaised, shape)
            .background(TmTheme.colors.accent.soft, shape)
            .border(2.dp, TmTheme.colors.accent.color, shape),
        contentAlignment = Alignment.Center
    ) {
        Text(text = hint, style = TmTheme.typography.title, color = TmTheme.colors.accent.color)
    }
}
