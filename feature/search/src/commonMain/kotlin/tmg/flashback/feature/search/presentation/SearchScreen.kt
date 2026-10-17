package tmg.flashback.feature.search.presentation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.search_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview
import tmg.flashback.navigation.Screen
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.textinput.TextInput
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    navigateTo: (Screen) -> Unit,
    filtering: List<Category>,
    viewModel: SearchViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(filtering) {
        viewModel.applyFilters(filtering)
    }

    SearchScreen(
        uiState = uiState.value,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        navigateTo = navigateTo,
        searchTerm = viewModel::searchTerm,
        refresh = viewModel::refresh
    )
}

@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    navigateTo: (Screen) -> Unit,
    searchTerm: (String) -> Unit,
    refresh: () -> Unit
) {
    val searchTerm = remember { mutableStateOf(TextFieldValue(uiState.searchTerm)) }
    SwipeRefresh(
        modifier = Modifier,
        isLoading = uiState.isLoading,
        onRefresh = refresh,
        windowSizeClass = windowSizeClass
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(250.dp),
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            item("header", span = { GridItemSpan(maxLineSpan) }) {
                Header(
                    modifier = Modifier.animateItem(),
                    actionUpClicked = actionUpClicked,
                    action = null,
                    text = stringResource(string.search_title),
                    overrideIcons = {
                        Refresh(onClick = refresh)
                    }
                )
            }
            item("search", span = { GridItemSpan(maxLineSpan) }) {
                TextInput(
                    modifier = Modifier
                        .animateItem()
                        .padding(
                            horizontal = AppTheme.dimens.medium,
                            vertical = AppTheme.dimens.xsmall
                        ),
                    text = searchTerm,
                    onValueChange = {
                        searchTerm.value = it
                        searchTerm(it.text)
                    },
                    clear = {
                        searchTerm("")
                    },
                    placeholder = stringResource(string.search_title)
                )
            }
            item("filters", span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    modifier = Modifier
                        .animateItem()
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(bottom = AppTheme.dimens.medium),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)

                ) {
                    Spacer(Modifier.width(AppTheme.dimens.nsmall))
                    for (cat in uiState.categories) {
                        BadgeView(
                            label = stringResource(cat.label),
                        )
                    }
                    Spacer(Modifier.width(AppTheme.dimens.nsmall))
                }
            }
            if (uiState.resultsDrivers.isNotEmpty()) {
                item("driver-header", span = { GridItemSpan(maxLineSpan) }) {
                    SearchHeader(
                        category = Category.Drivers,
                        modifier = Modifier.animateItem()
                    )
                }
            }
            items(uiState.resultsDrivers, key = { it.id }) {
                DriverEntry(
                    model = it,
                    driverClicked = { },
                    modifier = Modifier.animateItem()
                )
            }
            if (uiState.resultsConstructors.isNotEmpty()) {
                item("constructor-header", span = { GridItemSpan(maxLineSpan) }) {
                    SearchHeader(
                        category = Category.Teams,
                        modifier = Modifier.animateItem()
                    )
                }
            }
            items(uiState.resultsConstructors, key = { it.id }) {
                ConstructorEntry(
                    model = it,
                    constructorClicked = { },
                    modifier = Modifier.animateItem()
                )
            }
            if (uiState.resultsCircuits.isNotEmpty()) {
                item("circuits-header", span = { GridItemSpan(maxLineSpan) }) {
                    SearchHeader(
                        category = Category.Circuits,
                        modifier = Modifier.animateItem()
                    )
                }
            }
            items(uiState.resultsCircuits, key = { it.id }) {
                CircuitEntry(
                    model = it,
                    circuitClicked = { },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        SearchScreen(
            uiState = SearchUiState(
                categories = Category.entries,
                searchTerm = "f1",
                isLoading = false,
                resultsCircuits = listOf(
                    Result.ResultCircuit(Circuit.preview())
                ),
                resultsDrivers = listOf(
                    Result.ResultDriver(Driver.preview())
                ),
                resultsConstructors = listOf(
                    Result.ResultTeam(Constructor.preview())
                ),
            ),
            actionUpClicked = { },
            paddingValues = PaddingValues(0.dp),
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass,
            navigateTo = { },
            searchTerm = { },
            refresh = { }
        )
    }
}