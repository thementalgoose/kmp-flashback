package tmg.flashback.feature.season.presentation.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.app_name
import flashback.presentation.localisation.generated.resources.search_title
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_search
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.MonthNames
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.eastereggs.model.MenuIcons
import tmg.flashback.feature.highlights.presentation.HighlightBanner
import tmg.flashback.feature.notifications.presentation.NotificationPrompt
import tmg.flashback.feature.season.presentation.calendar.components.CollapsableList
import tmg.flashback.feature.season.presentation.calendar.components.RaceWeekCard
import tmg.flashback.feature.season.presentation.shared.device_time.DeviceTimePrompt
import tmg.flashback.feature.season.presentation.shared.providedby.ProvidedBy
import tmg.flashback.feature.season.presentation.shared.seasonpicker.ResultsSeasonPicker
import tmg.flashback.formula1.extensions.icon
import tmg.flashback.formula1.extensions.label
import tmg.flashback.infrastructure.datetime.displayDate
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.startOfWeek
import tmg.flashback.navigation.NavSearch
import tmg.flashback.navigation.NavSearchFilter
import tmg.flashback.navigation.NavWeekend
import tmg.flashback.navigation.Screen
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewDevices
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.loading.SkeletonViewList
import tmg.flashback.ui.components.now.Now
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh
import tmg.flashback.ui.navigation.appBarMaximumHeight

private const val listAlpha = 0.6f

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowSizeClass,
    navigateTo: (Screen) -> Unit,
    viewModel: CalendarScreenViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    ScreenView(screenName = "Calendar", updateKey = uiState.value.season, args = mapOf(
        analyticsSeason to uiState.value.season.toString()
    ))

    // Add custom padding for nav bar
    val insets = when (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        true -> paddingValues
        false -> {
            val direction = LocalLayoutDirection.current
            PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + appBarMaximumHeight,
                start = paddingValues.calculateStartPadding(direction),
                end = paddingValues.calculateEndPadding(direction)
            )
        }
    }

    CalendarScreen(
        paddingValues = insets,
        windowSizeClass = windowSizeClass,
        uiState = uiState.value,
        refresh = viewModel::refresh,
        goToSearch = {
            navigateTo(
                NavSearch(NavSearchFilter.Circuits)
            )
        },
        goToWeekend = { item, defaultTab ->
            navigateTo(
                NavWeekend(
                    season = item.model.season,
                    round = item.model.round,
                    raceName = item.model.raceName,
                    defaultTab = defaultTab
                )
            )
        },
        expandGroupedRaces = viewModel::clickGroupedRaces
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowSizeClass,
    uiState: CalendarScreenState,
    refresh: () -> Unit,
    expandGroupedRaces: () -> Unit,
    goToSearch: () -> Unit,
    goToWeekend: (CalendarItem.RaceWeek, String?) -> Unit,
) {
    val now = LocalDate.now()
    val eventsRef = remember(now.toEpochDays()) { LocalDate.now() }

    SwipeRefresh(
        isLoading = uiState.isLoading,
        onRefresh = refresh,
        windowSizeClass = windowSizeClass
    ) {
        val tooltipState = rememberTooltipState(
            isPersistent = true
        )
        val scope = rememberCoroutineScope()
        val labelText = uiState.menuIcon?.label?.let { stringResource(it) } ?: stringResource(string.app_name)
        val tooltip = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Below,
            spacingBetweenTooltipAndAnchor = 0.dp
        )
        LazyColumn(
            contentPadding = paddingValues,
            content = {
                item(key = "header") {
                    Header(
                        modifier = Modifier
                            .animateItem(),
                        content = {
                            ResultsSeasonPicker()
                        },
                        action = null,
                        overrideIcons = {
                            if (uiState.menuIcon != null) {
                                TooltipBox(
                                    positionProvider = tooltip,
                                    tooltip = {
                                        PlainTooltip {
                                            Text(labelText)
                                        }
                                    },
                                    state = tooltipState,
                                    modifier = Modifier,
                                    content = {
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    tooltipState.show()
                                                }
                                            },
                                            content = {
                                                Image(
                                                    painter = painterResource(uiState.menuIcon.drawable),
                                                    contentDescription = uiState.menuIcon.label?.let { stringResource(it) },
                                                )
                                            }
                                        )
                                    }
                                )
                            }
                            Refresh(onClick = refresh)
                            IconButton(
                                onClick = goToSearch,
                                content = {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_search),
                                        contentDescription = stringResource(string.search_title),
                                        tint = AppTheme.colors.onSurface
                                    )
                                }
                            )

                        }
                    )
                }

                if (uiState.items.isNullOrEmpty()) {
                    if (uiState.isLoading) {
                        item(key = "loading") {
                            SkeletonViewList(modifier = Modifier.animateItem())
                        }
                    }
                }

                item(key = "news") {
                    HighlightBanner(
                        modifier = Modifier.animateItem()
                    )
                }

                item(key = "notification_prompt") {
                    NotificationPrompt(
                        modifier = Modifier
                            .animateItem()
                            .padding(
                                horizontal = AppTheme.dimens.medium
                            )
                    )
                }

                item(key = "device_time") {
                    DeviceTimePrompt(
                        modifier = Modifier
                            .animateItem()
                            .padding(
                                vertical = AppTheme.dimens.xsmall,
                                horizontal = AppTheme.dimens.medium
                            )
                    )
                }

                items(uiState.items ?: emptyList(), key = { it.key }) { item ->
                    when (item) {
                        is CalendarItem.RaceWeek -> {
                            RaceWeekCard(
                                model = item,
                                itemClicked = { model, defaultTab -> goToWeekend(model, defaultTab) },
                                modifier = Modifier.animateItem()
                            )
                        }

                        is CalendarItem.Event -> {
                            Event(
                                event = item,
                                now = eventsRef,
                                modifier = Modifier.animateItem()
                            )
                        }

                        is CalendarItem.GroupedCompletedRaces -> {
                            CollapsableList(
                                model = item,
                                itemClicked = {
                                    expandGroupedRaces()
                                },
                                modifier = Modifier.animateItem()
                            )
                            Spacer(Modifier.height(AppTheme.dimens.xsmall))
                        }

                        is CalendarItem.EmptyWeek -> {
                            EmptyWeek(
                                model = item,
                                modifier = Modifier.animateItem()
                            )
                        }
                    }
                }

                item(key = "footer") {
                    ProvidedBy(
                        modifier = Modifier.animateItem()
                    )
                }
            }
        )
    }
}

@Composable
private fun EmptyWeek(
    model: CalendarItem.EmptyWeek,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.medium)
    ) {
        if (model.monday == LocalDate.now().startOfWeek()) {
            Now(
                Modifier
                    .align(Alignment.CenterStart)
                    .alpha(0.5f))
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .align(Alignment.Center)
                .padding(horizontal = AppTheme.dimens.medium)
                .background(AppTheme.colors.surfaceContainer5)
                .alpha(0.3f)
        )
    }
}

@Composable
private fun Event(
    event: CalendarItem.Event,
    now: LocalDate,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .alpha(if (event.date >= now) 1f else listAlpha)
        .padding(
            vertical = AppTheme.dimens.xsmall,
            horizontal = AppTheme.dimens.medium
        )
    ) {
        Icon(
            painter = painterResource(resource = event.event.type.icon),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = AppTheme.colors.onSurface
        )
        TextBody1(
            text = "${stringResource(resource = event.event.type.label)}: ${event.event.label}",
            modifier = Modifier
                .padding(horizontal = AppTheme.dimens.small)
                .weight(1f)
        )
        TextBody2(
            text = event.event.date.displayDate(
                month = MonthNames.ENGLISH_FULL,
                includeYear = false
            ),
        )
    }
}

@PreviewDevices
@Composable
private fun Preview() {
    ApplicationThemePreview {
        CalendarScreen(
            paddingValues = PaddingValues(0.dp),
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass,
            uiState = CalendarScreenState(
                season = 2026,
                items = emptyList(),
                isLoading = false,
                showEvents = false,
                menuIcon = MenuIcons.BONFIRE
            ),
            refresh = { },
            expandGroupedRaces = { },
            goToSearch = { },
            goToWeekend = { _, _ -> }
        )
    }
}
