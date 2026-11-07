package org.mr0btain.uikit.gallery.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.mr0btain.uikit.gallery.KitGallery
import org.mr0btain.uikit.theme.TmFonts
import org.mr0btain.uikit.theme.TmPalettes
import org.mr0btain.uikit.theme.TmTheme

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tm UI Kit",
        state = rememberWindowState(width = 1440.dp, height = 960.dp)
    ) {
        var palette by remember { mutableStateOf(TmPalettes.default) }
        var font by remember { mutableStateOf(TmFonts.default) }
        var dark by remember { mutableStateOf(true) }
        var solid by remember { mutableStateOf(false) }

        TmTheme(palette = palette, font = font, darkTheme = dark, reduceTransparency = solid) {
            KitGallery(
                palette = palette,
                onPaletteChange = { palette = it },
                font = font,
                onFontChange = { font = it },
                darkTheme = dark,
                onDarkThemeChange = { dark = it },
                reduceTransparency = solid,
                onReduceTransparencyChange = { solid = it }
            )
        }
    }
}
