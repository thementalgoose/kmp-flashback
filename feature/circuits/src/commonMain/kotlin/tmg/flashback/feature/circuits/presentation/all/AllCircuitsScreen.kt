package tmg.flashback.feature.circuits.presentation.all

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.search_category_circuits
import flashback.presentation.localisation.generated.resources.search_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.preview.preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.textinput.TextInput
import tmg.flashback.ui.components.flag.Flag
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

private val countryImageSize = 32.dp
private val trackSize = 48.dp

@Composable
fun AllCircuitsScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    circuitClicked: (Circuit) -> Unit,
    viewModel: AllCircuitsViewModel = koinViewModel()
) {
    ScreenView(screenName = "All Circuits")

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    val focusManager = LocalFocusManager.current
    val uiState = viewModel.uiState.collectAsState()
    AllCircuitsScreen(
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        uiState = uiState.value,
        enterSearchTerm = viewModel::enterSearchTerm,
        clearSearch = viewModel::clearSearch,
        refresh = viewModel::refresh,
        circuitClicked = {
            focusManager.clearFocus()
            circuitClicked(it.circuit)
        },
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    )
}

@Composable
fun AllCircuitsScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: AllCircuitsUiState,
    refresh: () -> Unit,
    enterSearchTerm: (String) -> Unit,
    clearSearch: () -> Unit,
    circuitClicked: (CircuitOverview) -> Unit,
    modifier: Modifier = Modifier,
) {
    val searchTerm = remember { mutableStateOf(TextFieldValue(uiState.searchQuery ?: "")) }
    SwipeRefresh(
        modifier = modifier,
        isLoading = uiState.isLoading,
        onRefresh = refresh
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 400.dp),
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            item("header", span = { GridItemSpan(maxLineSpan) }) {
                Header(
                    actionUpClicked = actionUpClicked,
                    action = HeaderAction.MENU.takeIf { windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT },
                    text = stringResource(string.search_category_circuits)
                )
            }
            item("search", span = { GridItemSpan(maxLineSpan) }) {
                TextInput(
                    modifier = Modifier.padding(
                        horizontal = AppTheme.dimens.medium,
                        vertical = AppTheme.dimens.xsmall
                    ),
                    text = searchTerm,
                    onValueChange = {
                        searchTerm.value = it
                        enterSearchTerm(it.text)
                    },
                    clear = clearSearch,
                    placeholder = stringResource(string.search_title)
                )
            }
            items(uiState.circuits, key = { it.circuit.id }) {
                Circuit(
                    model = it,
                    onClick = circuitClicked
                )
            }
        }
    }
}

@Composable
fun Circuit(
    model: CircuitOverview,
    onClick: (CircuitOverview) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier
        .clickable(
            onClick = { onClick(model) }
        )
        .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
        .padding(
            horizontal = AppTheme.dimens.medium,
            vertical = AppTheme.dimens.small
        )
    ) {
        Flag(
            modifier = Modifier
                .size(countryImageSize)
                .align(Alignment.CenterVertically),
            iso = model.circuit.countryISO,
            nationality = model.circuit.country
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(horizontal = AppTheme.dimens.nsmall),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextBody1(
                text = model.circuit.name,
                bold = true
            )
            TextBody2("${model.circuit.city}, ${model.circuit.country}")
        }
        if (model.track != null) {
            Icon(
                modifier = Modifier.size(trackSize),
                painter = painterResource(model.track.getDefaultIcon()),
                contentDescription = null,
                tint = AppTheme.colors.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        AllCircuitsScreen(
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            uiState = AllCircuitsUiState(
                isLoading = false,
                searchQuery = "Search Term",
                circuits = listOf(
                    CircuitOverview(Circuit.preview(), TrackLayout.SILVERSTONE),
                    CircuitOverview(Circuit.preview(id = "2"), TrackLayout.WATKINS_GLEN)
                )
            ),
            refresh = { },
            enterSearchTerm = { },
            clearSearch = { },
            circuitClicked = { }
        )
    }
}