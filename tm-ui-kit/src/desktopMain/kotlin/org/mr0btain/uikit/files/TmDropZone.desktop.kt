package org.mr0btain.uikit.files

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragData
import androidx.compose.ui.draganddrop.dragData
import java.io.File
import java.net.URI

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
actual fun TmDropZone(
    onDrop: (List<String>) -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    hint: String,
    content: @Composable BoxScope.() -> Unit
) {
    var hovering by remember { mutableStateOf(false) }
    val currentOnDrop by rememberUpdatedState(onDrop)
    val target = remember {
        object : DragAndDropTarget {
            override fun onEntered(event: DragAndDropEvent) {
                hovering = true
            }

            override fun onExited(event: DragAndDropEvent) {
                hovering = false
            }

            override fun onEnded(event: DragAndDropEvent) {
                hovering = false
            }

            override fun onDrop(event: DragAndDropEvent): Boolean {
                hovering = false
                // Only read at drop time: reading the list while dragging throws on some systems
                val uris = (event.dragData() as? DragData.FilesList)?.readFiles() ?: return false
                val paths = droppedPaths(uris)
                if (paths.isEmpty()) return false
                currentOnDrop(paths)
                return true
            }
        }
    }

    Box(
        modifier.dragAndDropTarget(
            shouldStartDragAndDrop = { enabled && it.dragData() is DragData.FilesList },
            target = target
        )
    ) {
        content()
        if (hovering) TmDropHighlight(hint)
    }
}

/** Dropped `file:` URIs as local paths; anything else (a link dragged from a browser) is left out */
internal fun droppedPaths(uris: List<String>): List<String> = uris.mapNotNull { uri ->
    runCatching { URI(uri) }.getOrNull()
        ?.takeIf { it.scheme == "file" }
        ?.let { runCatching { File(it).path }.getOrNull() }
}
