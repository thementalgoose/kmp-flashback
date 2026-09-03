package tmg.flashback.feature.glossary.presentation.all

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_glossary
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.formula1.constants.Glossary
import tmg.flashback.navigation.NavGlossaryDetail
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun GlossaryScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    navigateTo: (NavKey) -> Unit,
    viewModel: GlossaryViewModel = koinViewModel()
) {
    ScreenView(screenName = "Glossary")

    val focusManager = LocalFocusManager.current
    val uiState = viewModel.uiState.collectAsState()
    GlossaryScreen(
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        uiState = uiState.value,
        glossaryClicked = {
            focusManager.clearFocus()
            navigateTo(NavGlossaryDetail(it.id))
        },
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
    )
}

@Composable
private fun GlossaryScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: GlossaryUiState,
    glossaryClicked: (Glossary) -> Unit,
    modifier: Modifier = Modifier,
) {
    val searchTerm = remember { mutableStateOf(TextFieldValue(uiState.searchTerm ?: "")) }
    SwipeRefresh(
        modifier = modifier,
        isLoading = false,
        onRefresh = { },
        windowSizeClass = windowSizeClass
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(300.dp),
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            item("header", span = { GridItemSpan(maxLineSpan) }) {
                Header(
                    actionUpClicked = actionUpClicked,
                    action = HeaderAction.MENU.takeIf { !windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) },
                    text = stringResource(string.nav_glossary),
                    overrideIcons = { }
                )
            }
            items(uiState.entries, key = { it.name }) {
                GlossaryItem(
                    glossary = it,
                    modifier = Modifier
                        .clickable(onClick = { glossaryClicked(it) })
                        .padding(
                            horizontal = AppTheme.dimens.medium,
                            vertical = AppTheme.dimens.small
                        )
                        .animateItem()
                )
            }
        }
    }
}

@Composable
private fun GlossaryItem(
    glossary: Glossary,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {
        TextTitle(
            text = stringResource(glossary.label),
            modifier = Modifier
                .weight(1f)
        )
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        GlossaryScreen(
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            uiState = GlossaryUiState(
                searchTerm = "Search Term",
                entries = Glossary.entries
            ),
            glossaryClicked = { }
        )
    }
}