package org.mr0btain.uikit.files

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/** No file drag and drop here: just the content */
@Composable
actual fun TmDropZone(
    onDrop: (List<String>) -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    hint: String,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier, content = content)
}
