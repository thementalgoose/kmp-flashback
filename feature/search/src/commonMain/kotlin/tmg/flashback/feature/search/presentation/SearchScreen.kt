package tmg.flashback.feature.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.LayersClear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.domain.formula1.generated.resources.Res
import flashback.domain.formula1.generated.resources.ic_driver
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_clear_filters
import flashback.presentation.localisation.generated.resources.search_title
import flashback.presentation.localisation.generated.resources.weather_type_clear_sky
import flashback.presentation.ui.generated.resources.ic_close
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview
import tmg.flashback.navigation.NavCircuit
import tmg.flashback.navigation.NavDriver
import tmg.flashback.navigation.NavSearchFilter
import tmg.flashback.navigation.NavTeam
import tmg.flashback.navigation.Screen
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextHeadline3
import tmg.flashback.style.text.TextHeadline4
import tmg.flashback.style.textinput.TextInput
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    navigateTo: (Screen) -> Unit,
    filtering: NavSearchFilter?,
    viewModel: SearchViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(filtering) {
        val filters = filtering?.toCategory
        viewModel.applyFilter(filters)
    }

    SearchScreen(
        uiState = uiState.value,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        applyFilter = viewModel::applyFilter,
        navigateTo = navigateTo,
        clearFilter = filtering == null,
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
    applyFilter: (Category?) -> Unit,
    clearFilter: Boolean,
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
                    action = if (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) null else HeaderAction.BACK,
                    text = stringResource(uiState.selectedCategory?.label ?: string.search_title),
                    overrideIcons = {
                        Refresh(onClick = refresh)
                        if (clearFilter) {
                            IconButton(
                                onClick = {
                                    applyFilter(null)
                                },
                                content = {
                                    Icon(
                                        imageVector = Icons.Default.LayersClear,
                                        contentDescription = stringResource(string.ab_clear_filters),
                                        tint = AppTheme.colors.onSurface
                                    )
                                }
                            )
                        }
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

            if (uiState.results.isNotEmpty()) {
                items(uiState.results, key = { it.id }) {
                    when (it) {
                        is Result.ResultCircuit -> {
                            CircuitEntry(
                                model = it,
                                circuitClicked = {
                                    navigateTo(NavCircuit(
                                        id = it.id,
                                        name = it.name
                                    ))
                                },
                                modifier = Modifier.animateItem()
                            )
                        }
                        is Result.ResultDriver -> {
                            DriverEntry(
                                model = it,
                                driverClicked = {
                                    navigateTo(NavDriver(
                                        season = null,
                                        id = it.id,
                                        name = it.name
                                    ))
                                },
                                modifier = Modifier.animateItem()
                            )
                        }
//                            is Result.ResultRace -> TODO()
                        is Result.ResultTeam -> {
                            ConstructorEntry(
                                model = it,
                                constructorClicked = {
                                    navigateTo(NavTeam(
                                        season = null,
                                        id = it.id,
                                        name = it.name
                                    ))
                                },
                                modifier = Modifier.animateItem()
                            )
                        }
                        else -> {}
                    }
                }
            }
            if (uiState.selectedCategory == null) {
                items(Category.entries, key = { it.key }) {
                    Container(
                        title = stringResource(it.label),
                        modifier = Modifier.clickable(
                            onClick = { applyFilter(it) }
                        ),
                        icon = {
                            Icon(
                                painter = painterResource(it.icon),
                                tint = AppTheme.colors.onPrimaryContainer,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun Container(
    title: String,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = { },
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.xxsmall
            )
            .clip(RoundedCornerShape(6.dp))
            .background(AppTheme.colors.primaryContainer)
            .padding(
                start = AppTheme.dimens.medium,
                end = AppTheme.dimens.medium,
                top = AppTheme.dimens.medium,
                bottom = AppTheme.dimens.medium
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = AppTheme.dimens.small),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
            ) {
                icon?.invoke()
                TextHeadline4(
                    text = title.uppercase(),
                    bold = true,
                    italic = true
                )
            }
        }
        content()
    }
}

private val NavSearchFilter.toCategory: Category
    get() = when (this) {
        NavSearchFilter.Drivers -> Category.Drivers
        NavSearchFilter.Teams -> Category.Teams
        NavSearchFilter.Circuits -> Category.Circuits
//        NavSearchFilter.Races -> Category.Races
    }

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        SearchScreen(
            uiState = SearchUiState(
                selectedCategory = Category.Drivers,
                searchTerm = "f1",
                isLoading = false,
                results = listOf(
                    Result.ResultCircuit(Circuit.preview()),
                    Result.ResultDriver(Driver.preview()),
                    Result.ResultTeam(Constructor.preview())
                ),
            ),
            clearFilter = true,
            applyFilter = { },
            actionUpClicked = { },
            paddingValues = PaddingValues(0.dp),
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass,
            navigateTo = { },
            searchTerm = { },
            refresh = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewNoCategory() {
    ApplicationThemePreview {
        SearchScreen(
            uiState = SearchUiState(
                selectedCategory = null,
                searchTerm = "f1",
                isLoading = false,
                results = listOf(
                    Result.ResultCircuit(Circuit.preview()),
                    Result.ResultDriver(Driver.preview()),
                    Result.ResultTeam(Constructor.preview())
                ),
            ),
            clearFilter = false,
            actionUpClicked = { },
            paddingValues = PaddingValues(0.dp),
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass,
            navigateTo = { },
            applyFilter = { },
            searchTerm = { },
            refresh = { }
        )
    }
}