package org.mr0btain.uikit.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mr0btain.uikit.buttons.TmIconButton
import org.mr0btain.uikit.feedback.TmSnackbarHost
import org.mr0btain.uikit.foundation.TmBackdrop
import org.mr0btain.uikit.foundation.glassSource
import org.mr0btain.uikit.resources.Res
import org.mr0btain.uikit.resources.sidebar
import org.mr0btain.uikit.theme.TmTheme

/** Height of the phone bottom bar plus its margin, screens pad their bottom by this */
val TM_BOTTOM_NAV_SPACE = 80.dp

/**
 * The status and navigation bars and any camera cutout. The backdrop runs under them, everything else stays out.
 * Only Android has any; the keyboard isn't included, so the bottom bar stays behind it
 */
val tmShellInsets: WindowInsets
    @Composable get() = WindowInsets.systemBars.union(WindowInsets.displayCutout)

/**
 * The app's frame: [TmWideShell] (a nav rail beside the screen) when [wide], else [TmPhoneShell] (a bottom bar).
 * Doesn't know about any navigation library: give it the places, which one is open, and what picking one does
 * @param railFooter Below the rail's places on wide windows, like a user menu; told whether the rail is expanded
 */
@Composable
fun TmAppShell(
    items: List<TmNavItem>,
    selectedKey: String?,
    onSelect: (TmNavItem) -> Unit,
    snackbarState: SnackbarHostState,
    wide: Boolean,
    railFooter: @Composable ColumnScope.(expanded: Boolean) -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    if (wide) {
        TmWideShell(items, selectedKey, onSelect, snackbarState, railFooter = railFooter, content = content)
    } else {
        TmPhoneShell(items, selectedKey, onSelect, snackbarState, content = content)
    }
}

/**
 * Wide layout: a glass nav rail on the left, which expands to show labels, and the current screen beside it. The
 * screen is a glass source, so the rail and anything floating blurs it
 */
@Composable
fun TmWideShell(
    items: List<TmNavItem>,
    selectedKey: String?,
    onSelect: (TmNavItem) -> Unit,
    snackbarState: SnackbarHostState,
    modifier: Modifier = Modifier,
    railFooter: @Composable ColumnScope.(expanded: Boolean) -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    val spacing = TmTheme.spacing
    var expanded by rememberSaveable { mutableStateOf(false) }

    TmBackdrop(modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(tmShellInsets)
                .padding(spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {
            TmNavRail(
                items = items,
                selectedKey = selectedKey,
                onSelect = onSelect,
                expanded = expanded,
                header = {
                    TmIconButton(
                        icon = Res.drawable.sidebar,
                        contentDescription = if (expanded) "Collapse the sidebar" else "Expand the sidebar",
                        onClick = { expanded = !expanded }
                    )
                },
                footer = { railFooter(expanded) }
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .glassSource(),
                content = content
            )
        }

        TmSnackbarHost(state = snackbarState, modifier = Modifier.align(Alignment.BottomCenter).windowInsetsPadding(tmShellInsets))
    }
}

/** Phone layout: the current screen, with a glass bar of places floating at the bottom */
@Composable
fun TmPhoneShell(
    items: List<TmNavItem>,
    selectedKey: String?,
    onSelect: (TmNavItem) -> Unit,
    snackbarState: SnackbarHostState,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    TmBackdrop(modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .glassSource()
                .windowInsetsPadding(tmShellInsets)
                .padding(bottom = TM_BOTTOM_NAV_SPACE),
            content = content
        )

        TmSnackbarHost(
            state = snackbarState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(tmShellInsets)
                .padding(bottom = TM_BOTTOM_NAV_SPACE)
        )

        TmBottomNav(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(tmShellInsets)
                .padding(TmTheme.spacing.sm),
            items = items,
            selectedKey = selectedKey,
            onSelect = onSelect
        )
    }
}
