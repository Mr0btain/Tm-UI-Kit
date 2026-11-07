package org.mr0btain.uikit.rules

import java.io.File

/**
 * Keeps an app's screens on the UI kit. Colors, sizes, shapes and controls belong in the kit (or the app's own kit
 * extensions), so changing one there changes it everywhere. Each app runs [checkFiles] over its screen files in a
 * test; when it fails, use or add a kit component instead of styling the screen directly
 */
object KitRules {
    /** Material pieces screens may still use directly, the kit wraps everything else */
    private val allowedMaterial = setOf("Text", "Icon", "SnackbarHostState")

    /** Material subpackages screens may import from, for things that aren't drawn */
    private val allowedMaterialPackages = setOf("windowsizeclass")

    /** Named colors that don't pick a color */
    private val allowedNamedColors = setOf("Transparent", "Unspecified")

    private val bannedCode = listOf(
        Regex("""\bColor\(0x""") to "a hard coded color, use TmTheme.colors",
        Regex("""\bfontSize\s*=""") to "a hard coded text size, use TmTheme.typography",
        Regex("""\bTextStyle\(""") to "a hand made text style, use TmTheme.typography",
        Regex("""\bRoundedCornerShape\(""") to "a hand made shape, use TmTheme.shapes",
        Regex("""\bMaterialTheme\.""") to "Material's theme, use TmTheme"
    )

    private val namedColor = Regex("""\bColor\.([A-Z]\w*)""")
    private val materialImport = Regex("""^import androidx\.compose\.material3\.((?:[a-z]\w*\.)*)(\w+|\*)$""")
    private val material2Import = Regex("""^import androidx\.compose\.material\.""")

    /**
     * Every broken rule in [files], as `path:line reason` with the path relative to [root]. Empty when they all
     * follow the rules
     */
    fun checkFiles(root: File, files: List<File>): List<String> = files.flatMap { file ->
        check(file.readText()).map { (line, why) -> "${file.relativeTo(root)}:$line $why" }
    }

    /** Line number and reason for each broken rule in [code] */
    fun check(code: String): List<Pair<Int, String>> =
        withoutCommentsAndStrings(code).lines().mapIndexedNotNull { index, rawLine ->
            val line = rawLine.trim()
            problemIn(line)?.let { index + 1 to it }
        }

    private fun problemIn(line: String): String? {
        materialImport.find(line)?.let { match ->
            val subpackage = match.groupValues[1].trimEnd('.')
            val name = match.groupValues[2]
            return when {
                subpackage.isNotEmpty() && subpackage.substringBefore('.') !in allowedMaterialPackages ->
                    "imports Material's $subpackage.$name, use the kit's version"
                subpackage.isEmpty() && name !in allowedMaterial -> "imports Material's $name, use the kit's version"
                else -> null
            }
        }
        if (material2Import.containsMatchIn(line)) return "imports Material 2, use the kit"
        if (line.startsWith("import")) return null

        namedColor.findAll(line).map { it.groupValues[1] }.firstOrNull { it !in allowedNamedColors }?.let {
            return "uses Color.$it, use TmTheme.colors"
        }
        return bannedCode.firstOrNull { (code, _) -> code.containsMatchIn(line) }?.let { (_, why) -> "has $why" }
    }

    /**
     * Blanks out comments and string contents, keeping line breaks so line numbers still match. String templates
     * are blanked with the string, which is fine since nothing banned belongs in one
     */
    private fun withoutCommentsAndStrings(code: String): String = buildString {
        var i = 0
        while (i < code.length) {
            val c = code[i]
            when {
                code.startsWith("//", i) -> {
                    while (i < code.length && code[i] != '\n') i++
                    continue
                }
                code.startsWith("/*", i) -> {
                    val end = code.indexOf("*/", i + 2).let { if (it < 0) code.length else it + 2 }
                    code.substring(i, end).forEach { if (it == '\n') append('\n') }
                    i = end
                    continue
                }
                code.startsWith("\"\"\"", i) -> {
                    val end = code.indexOf("\"\"\"", i + 3).let { if (it < 0) code.length else it + 3 }
                    append("\"\"")
                    code.substring(i, end).forEach { if (it == '\n') append('\n') }
                    i = end
                    continue
                }
                c == '"' -> {
                    var j = i + 1
                    while (j < code.length && code[j] != '"' && code[j] != '\n') {
                        if (code[j] == '\\') j++
                        j++
                    }
                    append("\"\"")
                    i = minOf(j + 1, code.length)
                    continue
                }
                else -> append(c)
            }
            i++
        }
    }
}
