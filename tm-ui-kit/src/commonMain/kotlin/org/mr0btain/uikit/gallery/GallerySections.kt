package org.mr0btain.uikit.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.mr0btain.applogger.LogLevel
import org.mr0btain.applogger.LogLine
import org.mr0btain.uikit.buttons.TmButton
import org.mr0btain.uikit.buttons.TmButtonRow
import org.mr0btain.uikit.buttons.TmButtonVariant
import org.mr0btain.uikit.buttons.TmIconButton
import org.mr0btain.uikit.containers.TmConfirmDeleteDialog
import org.mr0btain.uikit.containers.TmConfirmDialog
import org.mr0btain.uikit.containers.TmDialog
import org.mr0btain.uikit.containers.TmDialogSurface
import org.mr0btain.uikit.containers.TmListRow
import org.mr0btain.uikit.containers.TmPageHeader
import org.mr0btain.uikit.containers.TmScrollColumn
import org.mr0btain.uikit.containers.TmSwitchRow
import org.mr0btain.uikit.containers.TmTabs
import org.mr0btain.uikit.containers.TmTextEditDialog
import org.mr0btain.uikit.data.TmColumn
import org.mr0btain.uikit.data.TmCountBar
import org.mr0btain.uikit.data.TmKeyValueRow
import org.mr0btain.uikit.data.TmLogTerminal
import org.mr0btain.uikit.data.TmStatTile
import org.mr0btain.uikit.data.TmTableHeader
import org.mr0btain.uikit.data.TmTableRow
import org.mr0btain.uikit.feedback.TmBadge
import org.mr0btain.uikit.feedback.TmDot
import org.mr0btain.uikit.feedback.TmEmptyState
import org.mr0btain.uikit.feedback.TmLoading
import org.mr0btain.uikit.feedback.TmNotice
import org.mr0btain.uikit.feedback.TmNoticeKind
import org.mr0btain.uikit.feedback.TmSnackbarContent
import org.mr0btain.uikit.foundation.TmControlSize
import org.mr0btain.uikit.foundation.TmDivider
import org.mr0btain.uikit.foundation.TmSurface
import org.mr0btain.uikit.foundation.TmSurfaceLevel
import org.mr0btain.uikit.inputs.TmFieldPair
import org.mr0btain.uikit.inputs.TmPasswordField
import org.mr0btain.uikit.inputs.TmTextField
import org.mr0btain.uikit.navigation.TmBottomNav
import org.mr0btain.uikit.navigation.TmNavItem
import org.mr0btain.uikit.navigation.TmNavRail
import org.mr0btain.uikit.navigation.TmStepper
import org.mr0btain.uikit.navigation.TmTopBar
import org.mr0btain.uikit.resources.Res
import org.mr0btain.uikit.resources.alert_triangle
import org.mr0btain.uikit.resources.archive
import org.mr0btain.uikit.resources.bell
import org.mr0btain.uikit.resources.edit_2
import org.mr0btain.uikit.resources.file_text
import org.mr0btain.uikit.resources.home
import org.mr0btain.uikit.resources.inbox
import org.mr0btain.uikit.resources.lock
import org.mr0btain.uikit.resources.mail
import org.mr0btain.uikit.resources.monitor
import org.mr0btain.uikit.resources.plus
import org.mr0btain.uikit.resources.printer
import org.mr0btain.uikit.resources.refresh_cw
import org.mr0btain.uikit.resources.settings
import org.mr0btain.uikit.resources.sidebar
import org.mr0btain.uikit.resources.tool
import org.mr0btain.uikit.resources.trash_2
import org.mr0btain.uikit.resources.user
import org.mr0btain.uikit.search.TmSearchField
import org.mr0btain.uikit.selection.TmCheckbox
import org.mr0btain.uikit.selection.TmDropdown
import org.mr0btain.uikit.selection.TmFilterChip
import org.mr0btain.uikit.selection.TmRadio
import org.mr0btain.uikit.selection.TmSegmented
import org.mr0btain.uikit.selection.TmSwitch
import org.mr0btain.uikit.theme.TmTheme
import org.mr0btain.uikit.theme.TmTone

/** The kit's own gallery sections in order. Apps add theirs after these with KitGallery's extraSections */
internal fun gallerySections(): List<@Composable () -> Unit> = listOf(
    { ButtonsSection() },
    { FieldsSection() },
    { SelectionSection() },
    { SearchSection() },
    { BadgesSection() },
    { ListsSection() },
    { TablesSection() },
    { FeedbackSection() },
    { DataSection() },
    { LogsSection() },
    { DialogSection() },
    { NavigationSection() },
    { ColorsSection() },
    { TypeSection() }
)

/** "SECONDARY" as "Secondary" */
private fun enumLabel(name: String): String = name.lowercase().replace('_', ' ').replaceFirstChar { it.uppercase() }

// --------------------------------------- Buttons ---------------------------------------

@Composable
private fun ButtonsSection() {
    GallerySection(title = "Buttons", description = "Primary for the one main action, ghost for the quiet ones") {
        TmControlSize.entries.forEach { size ->
            Specimen(enumLabel(size.name)) {
                TmButtonVariant.entries.forEach { variant ->
                    TmButton(text = enumLabel(variant.name), onClick = {}, variant = variant, size = size)
                }
            }
        }

        Specimen("With icons, loading and disabled") {
            TmButton(text = "New", onClick = {}, leadingIcon = Res.drawable.plus)
            TmButton(text = "Print", onClick = {}, variant = TmButtonVariant.SECONDARY, leadingIcon = Res.drawable.printer)
            TmButton(text = "Saving", onClick = {}, loading = true)
            TmButton(text = "Delete", onClick = {}, variant = TmButtonVariant.DANGER, leadingIcon = Res.drawable.trash_2)
            TmButton(text = "Disabled", onClick = {}, enabled = false)
        }

        Specimen("Icon buttons") {
            TmIconButton(icon = Res.drawable.edit_2, contentDescription = "Edit", onClick = {})
            TmIconButton(icon = Res.drawable.printer, contentDescription = "Print", onClick = {}, variant = TmButtonVariant.SECONDARY)
            TmIconButton(icon = Res.drawable.plus, contentDescription = "Add", onClick = {}, variant = TmButtonVariant.PRIMARY)
            TmIconButton(icon = Res.drawable.trash_2, contentDescription = "Delete", onClick = {}, variant = TmButtonVariant.DANGER)
            TmIconButton(icon = Res.drawable.refresh_cw, contentDescription = "Refresh", onClick = {}, size = TmControlSize.SMALL)
        }

        Specimen("Dialog actions") {
            TmButtonRow(confirmText = "Save changes", onConfirm = {}, onCancel = {})
        }
    }
}

// --------------------------------------- Fields ---------------------------------------

@Composable
private fun FieldsSection() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("dana@example.com") }
    var notes by remember { mutableStateOf("Longer text wraps onto more lines, and the field grows with it.") }
    var password by remember { mutableStateOf("bench") }

    GallerySection(title = "Fields", description = "Label above, help or error below") {
        Row(horizontalArrangement = Arrangement.spacedBy(TmTheme.spacing.md)) {
            TmTextField(modifier = Modifier.weight(1f), value = name, onValueChange = { name = it }, label = "Name", placeholder = "First and last name")
            TmTextField(
                modifier = Modifier.weight(1f),
                value = email,
                onValueChange = { email = it },
                label = "Email",
                leadingIcon = Res.drawable.mail,
                supportingText = "Receipts go here"
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(TmTheme.spacing.md)) {
            TmTextField(modifier = Modifier.weight(1f), value = "555 01", onValueChange = {}, label = "Phone", error = "Phone numbers need 10 digits")
            TmTextField(modifier = Modifier.weight(1f), value = "Locked by admin", onValueChange = {}, label = "Disabled", enabled = false)
        }

        TmTextField(value = notes, onValueChange = { notes = it }, label = "Notes", singleLine = false, minLines = 3)

        TmPasswordField(value = password, onValueChange = { password = it }, showStrength = true)

        TmFieldPair(
            { TmTextField(modifier = it, value = "", onValueChange = {}, label = "First name") },
            { TmTextField(modifier = it, value = "", onValueChange = {}, label = "Last name") }
        )

        Specimen("Large, for touch screens") {
            TmTextField(modifier = Modifier.width(320.dp), value = "", onValueChange = {}, placeholder = "Search", size = TmControlSize.LARGE)
            TmButton(text = "Find", onClick = {}, size = TmControlSize.LARGE)
        }
    }
}

// --------------------------------------- Selection ---------------------------------------

private enum class SampleState { OPEN, WAITING, DONE, CLOSED }

@Composable
private fun SelectionSection() {
    var state by remember { mutableStateOf<SampleState?>(null) }
    var view by remember { mutableStateOf("Queue") }
    var openOnly by remember { mutableStateOf(true) }
    var mine by remember { mutableStateOf(false) }
    var urgent by remember { mutableStateOf(false) }
    var switched by remember { mutableStateOf(true) }
    var checked by remember { mutableStateOf(true) }
    var radio by remember { mutableStateOf("Card") }

    GallerySection(title = "Choosing", description = "Dropdowns, segments, chips and toggles") {
        Row(horizontalArrangement = Arrangement.spacedBy(TmTheme.spacing.md)) {
            TmDropdown(
                modifier = Modifier.weight(1f),
                label = "State",
                options = SampleState.entries,
                selected = state,
                onSelect = { state = it },
                optionLabel = { enumLabel(it.name) },
                optionLeading = { TmDot(sampleTone(it).color) }
            )
            TmDropdown(
                modifier = Modifier.weight(1f),
                label = "Disabled",
                options = listOf("Only option"),
                selected = "Only option",
                onSelect = {},
                optionLabel = { it },
                enabled = false
            )
        }

        Specimen("Segmented") {
            TmSegmented(options = listOf("Queue", "Ready", "All"), selected = view, onSelect = { view = it }, optionLabel = { it })
        }

        Specimen("Filter chips") {
            TmFilterChip(text = "Open only", selected = openOnly, onClick = { openOnly = !openOnly })
            TmFilterChip(text = "Mine", selected = mine, onClick = { mine = !mine }, leadingIcon = Res.drawable.user)
            TmFilterChip(text = "Urgent", selected = urgent, onClick = { urgent = !urgent }, leadingIcon = Res.drawable.alert_triangle)
        }

        Specimen("Toggles") {
            TmSwitch(checked = switched, onCheckedChange = { switched = it }, contentDescription = "Sample switch")
            TmSwitch(checked = false, onCheckedChange = {}, enabled = false)
            TmCheckbox(checked = checked, onCheckedChange = { checked = it }, label = "Charge tax")
            TmCheckbox(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false)
        }

        Specimen("Radio") {
            listOf("Card", "Cash", "Credit").forEach { option ->
                TmRadio(selected = radio == option, onClick = { radio = option }, label = option)
            }
        }
    }
}

@Composable
private fun sampleTone(state: SampleState): TmTone = when (state) {
    SampleState.OPEN -> TmTheme.colors.accent
    SampleState.WAITING -> TmTheme.colors.warning
    SampleState.DONE -> TmTheme.colors.success
    SampleState.CLOSED -> TmTheme.colors.neutral
}

// --------------------------------------- Search ---------------------------------------

@Composable
private fun SearchSection() {
    var query by remember { mutableStateOf("") }

    GallerySection(title = "Search", description = "A search field, and one that's busy") {
        TmSearchField(query = query, onQueryChange = { query = it }, placeholder = "Search")
        TmSearchField(query = "busy", onQueryChange = {}, placeholder = "Search", loading = true)
    }
}

// --------------------------------------- Badges ---------------------------------------

@Composable
private fun BadgesSection() {
    GallerySection(title = "Badges", description = "Each tone keeps its color in every theme") {
        Specimen("Soft") { SampleState.entries.forEach { TmBadge(text = enumLabel(it.name), tone = sampleTone(it)) } }
        Specimen("Solid") { SampleState.entries.forEach { TmBadge(text = enumLabel(it.name), tone = sampleTone(it), solid = true) } }
        Specimen("No dot") { TmBadge(text = "Admin", tone = TmTheme.colors.accent, dot = false) }
    }
}

// --------------------------------------- Lists ---------------------------------------

@Composable
private fun ListsSection() {
    var tab by remember { mutableIntStateOf(0) }
    var selected by remember { mutableIntStateOf(1) }
    var remember by remember { mutableStateOf(true) }

    GallerySection(title = "Lists and tabs") {
        TmTabs(tabs = listOf("People", "Items", "History"), selectedIndex = tab, onSelect = { tab = it }, badges = mapOf(1 to 12))

        Column {
            listOf("Dana Whitfield", "Marcus Oyelaran", "Priya Raman").forEachIndexed { index, name ->
                TmListRow(
                    title = name,
                    subtitle = "${index + 2} items, last seen ${index + 1}w ago",
                    icon = Res.drawable.user,
                    selected = index == selected,
                    onClick = { selected = index },
                    trailing = { Text("${index + 1} open", style = TmTheme.typography.caption, color = TmTheme.colors.textMuted) }
                )
            }
        }

        TmDivider()

        Text("Scroll column, for panels that might not fit", style = TmTheme.typography.caption, color = TmTheme.colors.textMuted)
        TmScrollColumn(modifier = Modifier.fillMaxWidth().height(96.dp)) {
            listOf("First", "Second", "Third", "Fourth", "Fifth", "Sixth").forEach { label ->
                Text(label, style = TmTheme.typography.body, color = TmTheme.colors.text)
            }
        }

        TmDivider()

        TmSwitchRow(
            title = "Remember login",
            subtitle = "Save username and password after logging in",
            icon = Res.drawable.lock,
            checked = remember,
            onCheckedChange = { remember = it }
        )
        TmListRow(title = "Start tab", subtitle = "Tab shown on start", icon = Res.drawable.home, onClick = {}, trailing = {
            Text("People", style = TmTheme.typography.body, color = TmTheme.colors.textMuted)
        })
    }
}

// --------------------------------------- Tables and steps ---------------------------------------

private val sampleColumns = listOf(
    TmColumn("Number", width = 112.dp),
    TmColumn("Name"),
    TmColumn("State", width = 136.dp),
    TmColumn("Size", width = 88.dp, end = true)
)

@Composable
private fun TablesSection() {
    var selected by remember { mutableIntStateOf(0) }
    var step by remember { mutableIntStateOf(1) }
    var confirming by remember { mutableStateOf(false) }

    GallerySection(title = "Tables and steps", description = "Lists of records, and forms filled in order") {
        TmPageHeader(
            title = "Records",
            subtitle = "Page header, with an optional back button and actions",
            onBack = {},
            actions = { TmButton(text = "Refresh", onClick = {}, variant = TmButtonVariant.SECONDARY, leadingIcon = Res.drawable.refresh_cw) }
        )

        TmSurface(level = TmSurfaceLevel.SUNKEN, modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(TmTheme.spacing.sm)) {
                TmTableHeader(sampleColumns)
                SampleState.entries.forEachIndexed { index, state ->
                    TmTableRow(sampleColumns, selected = index == selected, onClick = { selected = index }) { column ->
                        when (column) {
                            0 -> Text("0x${(0x8000 + index * 0x40).toString(16).uppercase()}", style = TmTheme.typography.numeric, color = TmTheme.colors.text)
                            1 -> Text("Sample ${index + 1}", style = TmTheme.typography.label, color = TmTheme.colors.text)
                            2 -> TmBadge(text = enumLabel(state.name), tone = sampleTone(state))
                            3 -> Text("${16 * (index + 1)}", style = TmTheme.typography.numeric, color = TmTheme.colors.textMuted)
                        }
                    }
                }
            }
        }

        TmStepper(
            steps = listOf("Open", "Check", "Edit", "Review", "Save"),
            currentIndex = step,
            onSelect = { step = it },
            canOpen = { it <= step + 1 }
        )

        TmButton(text = "Ask before deleting", onClick = { confirming = true }, variant = TmButtonVariant.SECONDARY)

        if (confirming) {
            TmConfirmDialog(
                title = "Delete this record?",
                message = "This can't be undone.",
                confirmText = "Delete record",
                destructive = true,
                onConfirm = { confirming = false },
                onDismissRequest = { confirming = false }
            )
        }
    }
}

// --------------------------------------- Feedback ---------------------------------------

@Composable
private fun FeedbackSection() {
    GallerySection(title = "Messages", description = "Say what happened and what to do next") {
        TmNotice(kind = TmNoticeKind.INFO, text = "This device is waiting for an admin to approve it.")
        TmNotice(kind = TmNoticeKind.SUCCESS, text = "Saved, and a copy was sent.")
        TmNotice(kind = TmNoticeKind.WARNING, title = "Running low", text = "Only 2 left.")
        TmNotice(kind = TmNoticeKind.ERROR, title = "Couldn't reach the server", text = "Check the address in Settings, then try again.")

        Specimen("Snackbar") {
            TmSnackbarContent(message = "Record saved", actionLabel = "Undo")
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(TmTheme.spacing.md)) {
            TmSurface(Modifier.weight(1f), level = TmSurfaceLevel.SUNKEN) {
                TmEmptyState(
                    icon = Res.drawable.inbox,
                    title = "Nothing here yet",
                    message = "New records show up here.",
                    actionText = "Add one"
                )
            }
            TmSurface(Modifier.weight(1f), level = TmSurfaceLevel.SUNKEN) {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    TmLoading(text = "Loading")
                }
            }
        }
    }
}

// --------------------------------------- Data ---------------------------------------

@Composable
private fun DataSection() {
    GallerySection(title = "Numbers", description = "Counts and values, with numbers in the monospace face") {
        Row(horizontalArrangement = Arrangement.spacedBy(TmTheme.spacing.md)) {
            TmStatTile(modifier = Modifier.weight(1f), label = "Open", value = "18", icon = Res.drawable.tool, caption = "4 waiting")
            TmStatTile(modifier = Modifier.weight(1f), label = "Today", value = "1,284", icon = Res.drawable.file_text, caption = "11 changes")
            TmStatTile(modifier = Modifier.weight(1f), label = "Problems", value = "3", icon = Res.drawable.alert_triangle, caption = "Need a look", attention = true)
        }

        Column {
            TmCountBar(label = "In progress", count = 9, total = 18, color = TmTheme.colors.warning.color)
            TmCountBar(label = "On hold", count = 4, total = 18, color = TmTheme.colors.danger.color)
            TmCountBar(label = "Done", count = 5, total = 18, color = TmTheme.colors.success.color)
        }

        TmDivider()

        TmKeyValueRow(label = "Name", value = "Sample record")
        TmKeyValueRow(label = "Address", value = "0x0008_4F20", numeric = true)
    }
}

// --------------------------------------- Logs ---------------------------------------

// A minute of a made up log
private val sampleLogLines = run {
    val start = 1_793_980_800_000L
    listOf(
        Triple(LogLevel.INFO, "App", "Started"),
        Triple(LogLevel.INFO, "Server", "Responding at http://0.0.0.0:8080"),
        Triple(LogLevel.DEBUG, "Database:Save", "Saved record 42, revision 4"),
        Triple(LogLevel.WARN, "Import", "Unknown keyword FOO on line 120, kept as is"),
        Triple(LogLevel.INFO, "Notify", "Sent a message"),
        Triple(LogLevel.ERROR, "Mail", "Couldn't reach smtp.example.com\njava.net.ConnectException: Connection refused\n\tat java.base/sun.nio.ch.Net.connect0(Native Method)"),
        Triple(LogLevel.INFO, "Housekeeping", "Removed 3 expired sessions")
    ).mapIndexed { index, (level, tag, message) ->
        LogLine(sequence = index + 1L, time = start + index * 7_300L, level = level, tag = tag, message = message)
    }
}

@Composable
private fun LogsSection() {
    GallerySection(title = "Log terminal", description = "A log as it's written, always on the palette's dark colors") {
        TmLogTerminal(
            lines = sampleLogLines,
            title = "app — server",
            status = "Live",
            highlight = "smtp",
            modifier = Modifier.fillMaxWidth().height(220.dp)
        )
        TmLogTerminal(lines = emptyList(), title = "app — this device", status = "Paused", live = false, modifier = Modifier.fillMaxWidth().height(90.dp))
    }
}

// --------------------------------------- Dialog ---------------------------------------

@Composable
private fun DialogSection() {
    var open by remember { mutableStateOf(false) }
    var confirmDelete by remember { mutableStateOf(false) }
    var editText by remember { mutableStateOf(false) }

    GallerySection(title = "Dialog", description = "Glass sheet, actions at the bottom right") {
        TmDialogSurface(
            modifier = Modifier.fillMaxWidth(),
            scrollable = false,
            title = "Delete this record?",
            description = "Its notes are deleted too. This can't be undone.",
            actions = {
                TmButtonRow(confirmText = "Delete", confirmVariant = TmButtonVariant.DANGER, onConfirm = {}, onCancel = {})
            }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(TmTheme.spacing.sm)) {
            TmButton(text = "Open as a real dialog", onClick = { open = true }, variant = TmButtonVariant.SECONDARY)
            TmButton(text = "Two step delete", onClick = { confirmDelete = true }, variant = TmButtonVariant.SECONDARY)
            TmButton(text = "Edit one value", onClick = { editText = true }, variant = TmButtonVariant.SECONDARY)
        }

        TmConfirmDeleteDialog(
            itemName = "Record",
            isTwoStep = true,
            isVisible = confirmDelete,
            onConfirm = { confirmDelete = false },
            onCancel = { confirmDelete = false }
        )

        TmTextEditDialog(
            label = "Name",
            value = "Sample",
            visible = editText,
            onConfirm = { editText = false; true },
            onCancel = { editText = false },
            keyboardType = KeyboardType.Text,
            hint = "Shown at the top of every page"
        )

        if (open) {
            TmDialog(
                title = "Change password",
                description = "Used to sign in.",
                onDismissRequest = { open = false },
                actions = { TmButtonRow(confirmText = "Save", onConfirm = { open = false }, onCancel = { open = false }) }
            ) {
                TmPasswordField(value = "", onValueChange = {}, label = "Current password")
                TmPasswordField(value = "", onValueChange = {}, label = "New password", showStrength = true)
            }
        }
    }
}

// --------------------------------------- Navigation ---------------------------------------

private val sampleNav = listOf(
    TmNavItem("main", "Main", Res.drawable.home),
    TmNavItem("screen", "Screen", Res.drawable.monitor),
    TmNavItem("files", "Files", Res.drawable.file_text),
    TmNavItem("archive", "Archive", Res.drawable.archive),
    TmNavItem("settings", "Settings", Res.drawable.settings)
)

@Composable
private fun NavigationSection() {
    var selected by remember { mutableStateOf("main") }

    GallerySection(title = "Navigation", description = "Glass bars that float over the screen") {
        TmTopBar(
            title = "Main",
            subtitle = "This device",
            navigation = { TmIconButton(icon = Res.drawable.sidebar, contentDescription = "Show or hide the sidebar", onClick = {}) },
            actions = { TmIconButton(icon = Res.drawable.bell, contentDescription = "Notifications", onClick = {}) }
        )

        Row(Modifier.height(400.dp), horizontalArrangement = Arrangement.spacedBy(TmTheme.spacing.md)) {
            TmNavRail(items = sampleNav, selectedKey = selected, onSelect = { selected = it.key })
            TmNavRail(
                items = sampleNav,
                selectedKey = selected,
                onSelect = { selected = it.key },
                expanded = true,
                footer = { TmListRow(title = "Dana W.", subtitle = "Admin", icon = Res.drawable.user) }
            )
        }

        TmBottomNav(items = sampleNav.take(4), selectedKey = selected, onSelect = { selected = it.key })
    }
}

// --------------------------------------- Theme ---------------------------------------

@Composable
private fun ColorsSection() {
    val colors = TmTheme.colors

    GallerySection(title = "Colors", description = "The current theme's layers and tones") {
        Specimen("Layers, back to front") {
            Swatch("Background", colors.background)
            Swatch("Surface", colors.surface)
            Swatch("Raised", colors.surfaceRaised)
            Swatch("Sunken", colors.surfaceSunken)
            Swatch("Glass", colors.glass)
        }

        Specimen("Text") {
            Swatch("Text", colors.text)
            Swatch("Muted", colors.textMuted)
            Swatch("Faint", colors.textFaint)
        }

        Specimen("Tones") {
            ToneSwatch("Accent", colors.accent)
            ToneSwatch("Success", colors.success)
            ToneSwatch("Warning", colors.warning)
            ToneSwatch("Danger", colors.danger)
            ToneSwatch("Neutral", colors.neutral)
        }
    }
}

@Composable
private fun Swatch(name: String, color: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(TmTheme.spacing.xs)) {
        Box(
            Modifier
                .size(width = 72.dp, height = 40.dp)
                .background(color, TmTheme.shapes.chip)
                .border(1.dp, TmTheme.colors.hairline, TmTheme.shapes.chip)
        )
        Text(name, style = TmTheme.typography.caption, color = TmTheme.colors.textMuted)
    }
}

@Composable
private fun ToneSwatch(name: String, tone: TmTone) {
    Column(verticalArrangement = Arrangement.spacedBy(TmTheme.spacing.xs)) {
        Row {
            Box(Modifier.size(width = 36.dp, height = 40.dp).background(tone.color, TmTheme.shapes.chip))
            Box(
                Modifier.size(width = 36.dp, height = 40.dp).background(tone.soft, TmTheme.shapes.chip),
                contentAlignment = Alignment.Center
            ) {
                Text("Aa", style = TmTheme.typography.label, color = tone.color)
            }
        }
        Text(name, style = TmTheme.typography.caption, color = TmTheme.colors.textMuted)
    }
}

@Composable
private fun TypeSection() {
    val type = TmTheme.typography
    val color = TmTheme.colors.text

    GallerySection(title = "Type", description = "Five sizes: 28, 20, 16, 14 and 12") {
        Text("Display, 28", style = type.display, color = color)
        Text("Title, 20", style = type.title, color = color)
        Text("Subtitle, 16", style = type.subtitle, color = color)
        Text("Body large, 16. The quick brown fox jumps over the lazy dog.", style = type.bodyLarge, color = color)
        Text("Body, 14. Pack my box with five dozen liquor jugs.", style = type.body, color = color)
        Text("Label, 14", style = type.label, color = color)
        Text("Caption, 12", style = type.caption, color = TmTheme.colors.textMuted)
        TmDivider()
        Text("0x0008_4F20   1,284.50   A5 5A FF 00", style = type.numeric, color = color)
        Text("1,284.50", style = type.numericDisplay, color = color)
    }
}
