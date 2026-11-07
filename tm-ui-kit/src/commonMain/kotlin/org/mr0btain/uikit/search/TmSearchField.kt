package org.mr0btain.uikit.search

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import org.mr0btain.uikit.buttons.TmIconButton
import org.mr0btain.uikit.foundation.TmControlSize
import org.mr0btain.uikit.inputs.TmTextField
import org.mr0btain.uikit.theme.TmTheme
import org.mr0btain.uikit.resources.Res
import org.mr0btain.uikit.resources.search
import org.mr0btain.uikit.resources.x
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/** How long typing has to pause before a search runs */
val TM_SEARCH_DEBOUNCE: Duration = 350.milliseconds

/**
 * Text field with a search icon and a clear button
 * @param onSearch Called when enter is pressed
 * @param loading Shows a spinner in place of the clear button
 */
@Composable
fun TmSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    label: String? = null,
    enabled: Boolean = true,
    loading: Boolean = false,
    size: TmControlSize = TmControlSize.MEDIUM,
    onSearch: (String) -> Unit = {}
) {
    TmTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        size = size,
        leadingIcon = Res.drawable.search,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
        trailing = {
            when {
                loading -> CircularProgressIndicator(
                    modifier = Modifier.size(size.iconSize),
                    color = TmTheme.colors.accent.color,
                    strokeWidth = 2.dp
                )

                query.isNotEmpty() -> TmIconButton(
                    icon = Res.drawable.x,
                    contentDescription = "Clear search",
                    onClick = { onQueryChange("") },
                    size = TmControlSize.SMALL
                )
            }
        }
    )
}
