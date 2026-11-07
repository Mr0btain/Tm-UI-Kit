package org.mr0btain.uikit.rules

import java.io.File
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals

class KitRulesTest {
    @Test
    fun rulesCatchWhatTheyShould() {
        val code = """
            import androidx.compose.material3.Text
            import androidx.compose.material3.Button
            import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
            import androidx.compose.material3.pulltorefresh.PullToRefreshBox
            import androidx.compose.material.Card
            val a = Color.Red
            val b = Color.Transparent
            val c = TextStyle(fontWeight = FontWeight.Bold)
            val d = "Color(0x in a string is fine"
            // RoundedCornerShape(4.dp) in a comment is fine
            /* fontSize = 12.sp
               MaterialTheme.colors */
            val e = RoundedCornerShape(4.dp) // and this isn't
        """.trimIndent()

        assertEquals(listOf(2, 4, 5, 6, 8, 13), KitRules.check(code).map { it.first })
    }

    @Test
    fun filesAreNamedRelativeToTheRoot() {
        val root = Files.createTempDirectory("kit-rules").toFile()
        try {
            val screen = File(root, "screens/Main.kt").apply {
                parentFile.mkdirs()
                writeText("val ok = 1\nval bad = Color(0xFF000000)\n")
            }

            assertEquals(listOf("screens/Main.kt:2 has a hard coded color, use TmTheme.colors"), KitRules.checkFiles(root, listOf(screen)))
        } finally {
            root.deleteRecursively()
        }
    }
}
