package tmg.flashback.feature.drivers.presentation.season

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.drivers.presentation.shared.DriverBadges
import tmg.flashback.feature.drivers.presentation.shared.DriverCard
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

data class DriverSeasonInfo(
    val season: Int,
    val id: String,
    val name: String,
)

@Composable
fun DriverSeasonScreen(
    driverSeasonInfo: DriverSeasonInfo,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    viewModel: DriverSeasonViewModel = koinViewModel()
) {
    LaunchedEffect(driverSeasonInfo) {
        viewModel.load(driverSeasonInfo.season, driverSeasonInfo.id)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val loading = viewModel.loading.collectAsStateWithLifecycle()

    DriverSeasonScreen(
        driverSeasonInfo = driverSeasonInfo,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        showBack = showBack,
        uiState = uiState.value,
        refresh = viewModel::refresh,
        isLoading = loading.value,
    )
}

@Composable
fun DriverSeasonScreen(
    driverSeasonInfo: DriverSeasonInfo,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    isLoading: Boolean,
    uiState: DriverSeasonUiState?,
    windowSizeClass: WindowSizeClass,
    refresh: () -> Unit
) {
    val direction = LocalLayoutDirection.current
    val bottomOnlyPadding = PaddingValues(
        start = paddingValues.calculateStartPadding(direction),
        end = paddingValues.calculateEndPadding(direction),
        bottom = paddingValues.calculateBottomPadding()
    )
    SwipeRefresh(
        isLoading = isLoading,
        onRefresh = refresh
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = bottomOnlyPadding
        ) {
            val driver = when (uiState) {
                is DriverSeasonUiState.Data -> uiState.driver
                is DriverSeasonUiState.NoRaces -> uiState.driver
                else -> null
            }
            item("header", span = { GridItemSpan(maxLineSpan) }) {
                DriverCard(
                    label = "${driverSeasonInfo.name}\n${driverSeasonInfo.season}",
                    driverImage = driver?.photoUrl,
                    insetPadding = paddingValues,
                    showBack = showBack,
                    colour = (uiState as? DriverSeasonUiState.Data)?.latestConstructor?.colour ?: AppTheme.colors.primary,
                    backClicked = actionUpClicked
                )
            }

            when (uiState) {
                is DriverSeasonUiState.Data -> {
                    item("badges", span = { GridItemSpan(maxLineSpan) }) {
                        DriverBadges(
                            driver = uiState.driver,
                            constructors = uiState.constructors
                        )
                    }
                    items(uiState.stats) {
                        Stat(it)
                    }
                    items(uiState.races, key = { "${it.result.raceInfo.season}-${it.result.raceInfo.round}-${it.result.isSprint}}" }) {

                    }
                }
                is DriverSeasonUiState.NoRaces -> {
                    item("badges", span = { GridItemSpan(maxLineSpan) }) {
                        DriverBadges(
                            driver = uiState.driver,
                            constructors = emptyList()
                        )
                    }
                }
                DriverSeasonUiState.NotFound -> {
                    item("not_found", span = { GridItemSpan(maxLineSpan) }) {

                    }
                }
                else -> { }
            }
        }
    }
}

@Composable
private fun Stat(
    model: DriverSeasonStat,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            )
            .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
            .background(AppTheme.colors.surfaceContainer3)
            .padding(AppTheme.dimens.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .background(AppTheme.colors.surfaceContainer4)
                .clip(CircleShape),
            painter = painterResource(model.icon),
            contentDescription = null,
            tint = AppTheme.colors.onSurface
        )
        Column(
            modifier = Modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextTitle(
                text = model.value,
                bold = true
            )
            HorizontalDivider()
            TextBody2(
                text = stringResource(model.string),
            )
        }
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {

    }
}