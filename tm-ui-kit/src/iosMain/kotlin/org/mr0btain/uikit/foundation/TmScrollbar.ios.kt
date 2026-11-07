package org.mr0btain.uikit.foundation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun TmVerticalScrollbar(state: LazyListState, modifier: Modifier) {
}

@Composable
actual fun TmVerticalScrollbar(state: ScrollState, modifier: Modifier) {
}

actual val tmShowsScrollbars: Boolean = false
