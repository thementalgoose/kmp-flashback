package tmg.flashback.feature.weekend.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_MEDIUM_LOWER_BOUND
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.weekend.presentation.WeekendUiState.Data
import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.feature.weekend.presentation.data.info.InfoModel
import tmg.flashback.feature.weekend.presentation.data.info.RaceDetails
import tmg.flashback.feature.weekend.presentation.data.info.RaceLinks
import tmg.flashback.feature.weekend.presentation.data.info.Schedule
import tmg.flashback.feature.weekend.presentation.data.qualifying.addQualifyingData
import tmg.flashback.feature.weekend.presentation.data.race.addRaceData
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.addSprintQualifyingData
import tmg.flashback.feature.weekend.presentation.data.sprint_race.addSprintRaceData
import tmg.flashback.formula1.model.Location
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.navigation.NavWeekend
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextHeadline1
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.flag.Flag
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.loading.SkeletonBox
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh
import tmg.flashback.ui.navigation.FloatingNavigationBar
import tmg.flashback.ui.navigation.NavigationBar
import tmg.flashback.ui.navigation.NavigationItem
import tmg.flashback.ui.navigation.appBarMaximumHeight

@Composable
fun WeekendScreen(
    data: NavWeekend,
    paddingValues: PaddingValues,
    showBack: Boolean,
    actionUpClicked: () -> Unit,
    navigateTo: (NavKey) -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: WeekendViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    LaunchedEffect(data) {
        viewModel.load(
            season = data.season,
            round = data.round
        )
    }

    ScreenView(
        updateKey = data,
        updateKey2 = (uiState.value as? Data)?.tab,
        shouldReport = { (uiState.value as? Data)?.tab != null },
        screenName = "Weekend", args = mapOf(
            AnalyticsConstants.analyticsSeason to data.season.toString(),
            AnalyticsConstants.analyticsRound to data.round.toString(),
            AnalyticsConstants.analyticsTab to ((uiState.value as? Data)?.tab?.id ?: "")
        )
    )

    WeekendScreenTab(
        isLoading = isLoading.value,
        screenData = data,
        paddingValues = paddingValues,
        showBack = showBack,
        navigateTo = navigateTo,
        actionUpClicked = actionUpClicked,
        openLink = viewModel::openLink,
        openMap = viewModel::openMap,
        windowSizeClass = windowSizeClass,
        uiState = uiState.value,
        clickWeekendTab = viewModel::updateTab,
        selectResultType = viewModel::selectResultType,
        refresh = viewModel::refresh
    )
}

@Composable
fun WeekendScreenTab(
    screenData: NavWeekend,
    isLoading: Boolean,
    paddingValues: PaddingValues,
    showBack: Boolean,
    navigateTo: (NavKey) -> Unit,
    actionUpClicked: () -> Unit,
    clickWeekendTab: (WeekendTabs) -> Unit,
    openLink: (String) -> Unit,
    openMap: (Location, String) -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: WeekendUiState,
    selectResultType: (ResultType) -> Unit,
    refresh: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SwipeRefresh(
            isLoading = isLoading,
            onRefresh = refresh,
            content = {
                // Add custom padding for nav bar
                val direction = LocalLayoutDirection.current
                val topInset = paddingValues.calculateTopPadding()
                val masterPadding = PaddingValues(
                    top = 0.dp,
                    bottom = paddingValues.calculateBottomPadding() + appBarMaximumHeight,
                    start = paddingValues.calculateStartPadding(direction),
                    end = paddingValues.calculateEndPadding(direction)
                )

                // Header remember stuff
                val imageUrl = (uiState as? Data)?.info?.aerialUrl
                val backgroundAlpha = animateColorAsState(
                    if (imageUrl != null) AppTheme.colors.surface.copy(alpha = 0.6f) else Color.Transparent
                )
                val height = 0.dp
                val painter = rememberAsyncImagePainter(
                    model = imageUrl,
                    contentScale = ContentScale.Crop
                )
                val painterState = painter.state.collectAsState()

                LazyColumn(
                    contentPadding = masterPadding,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item("header") {
                        val buttonModifier = Modifier
                            .clip(CircleShape)
                            .background(backgroundAlpha.value)
                        Box(modifier = Modifier
                            .animateItem()
                            .height(IntrinsicSize.Min)
                        ) {
                            Crossfade(
                                targetState = painterState.value,
                                modifier = Modifier.matchParentSize(),
                            ) {
                                when (it) {
                                    AsyncImagePainter.State.Empty -> {
                                        Box(Modifier.fillMaxSize())
                                    }
                                    is AsyncImagePainter.State.Error -> {
                                        Box(Modifier.fillMaxSize())
                                    }
                                    is AsyncImagePainter.State.Loading -> {
                                        SkeletonBox(Modifier.fillMaxSize())
                                    }
                                    is AsyncImagePainter.State.Success -> {
                                        Image(
                                            painter = it.painter,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                            val text = when {
                                uiState is Data -> "${uiState.info.season} ${uiState.info.raceName}"
                                else -> "${screenData.season} ${screenData.raceName}"
                            }
                            Header(
                                actionUpClicked = actionUpClicked,
                                action = HeaderAction.BACK.takeIf { showBack },
                                actionModifier = buttonModifier,
                                contentSpacing = height,
                                content = @Composable {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = AppTheme.dimens.medium,
                                                end = AppTheme.dimens.medium,
                                                top = AppTheme.dimens.medium,
                                                bottom = AppTheme.dimens.medium
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        TextHeadline1(
                                            text = text,
                                            modifier = Modifier
                                                .weight(1f)
                                        )
                                        if (uiState is Data) {
                                            Flag(
                                                iso = uiState.info.circuit.countryISO,
                                                nationality = uiState.info.circuit.country,
                                                modifier = Modifier.size(48.dp),
                                            )
                                        } else {
                                            Box(modifier = Modifier.size(48.dp))
                                        }
                                    }
                                },
                                scrim = true,
                                topInset = topInset,
                                overrideIcons = {
                                    Refresh(
                                        onClick = refresh,
                                        modifier = buttonModifier
                                    )
                                }
                            )
                        }
                    }

                    if (uiState is Data) {

                        addDetails(uiState.info)
                        addLinks(
                            info = uiState.info,
                            previousRace = uiState.previousRace,
                            previousRaceClicked = {
                                navigateTo(
                                    NavWeekend(
                                        season = it.season,
                                        round = it.round,
                                        raceName = it.raceName
                                    )
                                )
                            },
                            youtubeClicked = openLink,
                            wikipediaClicked = openLink,
                            mapsClicked = openMap
                        )
                        addSchedule(uiState.info)

                        if (uiState.tab == WeekendTabs.Qualifying) {
                            addQualifyingData(
                                uiState = uiState
                            )
                        }
                        if (uiState.tab == WeekendTabs.Race) {
                            addRaceData(
                                uiState = uiState,
                                selectResultType = selectResultType,
                            )
                        }
                        if (uiState.tab == WeekendTabs.SprintQualifying) {
                            addSprintQualifyingData(
                                uiState = uiState
                            )
                        }
                        if (uiState.tab == WeekendTabs.SprintRace) {
                            addSprintRaceData(
                                uiState = uiState,
                                selectResultType = selectResultType,
                            )
                        }
                    }
                }
            }
        )
        if (uiState is Data) {
            val navigationItems = uiState.tabs.toNavigationItem(uiState.tab)
            FloatingNavigationBar(
                modifier = Modifier
                    .background(Brush.verticalGradient(listOf(Color.Transparent, AppTheme.colors.surface)))
                    .padding(horizontal = AppTheme.dimens.medium)
                    .align(Alignment.BottomCenter),
                list = navigationItems,
                itemClicked = {
                    val tab = it.id.toEnum<WeekendTabs> { it.id }
                    if (tab != null) {
                        clickWeekendTab(tab)
                    }
                },
                bottomPadding = paddingValues.calculateBottomPadding() + 16.dp
            )
        }
    }
}

private fun List<WeekendTabs>.toNavigationItem(selected: WeekendTabs): List<NavigationItem> {
    return this.map {
        NavigationItem(
            id = it.id,
            label = it.label,
            icon = it.icon,
            selectedIcon = it.selectedIcon,
            isSelected = selected == it
        )
    }
}

fun LazyListScope.addDetails(info: InfoModel) {
    item("details") {
        RaceDetails(
            model = info,
            modifier = Modifier
                .animateItem()
                .padding(
                    horizontal = AppTheme.dimens.medium,
                    vertical = AppTheme.dimens.xsmall
                )
        )
    }
}

fun LazyListScope.addLinks(
    info: InfoModel,
    previousRace: OverviewRace?,
    previousRaceClicked: (OverviewRace) -> Unit,
    youtubeClicked: (String) -> Unit,
    wikipediaClicked: (String) -> Unit,
    mapsClicked: (Location, String) -> Unit
) {
    item("links") {
        RaceLinks(
            modifier = Modifier.animateItem(),
            model = info,
            previousRace = previousRace,
            previousRaceClicked = previousRaceClicked,
            youtubeClicked = youtubeClicked,
            wikipediaClicked = wikipediaClicked,
            mapsClicked = mapsClicked
        )
    }
}

fun LazyListScope.addSchedule(info: InfoModel) {
    item("schedule") {
        Schedule(
            modifier = Modifier.animateItem(),
            model = info
        )
    }
}