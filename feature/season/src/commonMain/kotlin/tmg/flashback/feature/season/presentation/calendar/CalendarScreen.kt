package tmg.flashback.feature.season.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import androidx.window.core.layout.WindowWidthSizeClass
import flashback.domain.formula1.generated.resources.Res.drawable
import flashback.domain.formula1.generated.resources.ic_tyre
import flashback.feature.season.generated.resources.ic_collapsible_icon_bottom
import flashback.feature.season.generated.resources.ic_collapsible_icon_top
import flashback.feature.season.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_collapsed_section
import flashback.presentation.localisation.generated.resources.tyres_label
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.highlights.presentation.HighlightBanner
import tmg.flashback.feature.notifications.presentation.NotificationPrompt
import tmg.flashback.feature.season.presentation.calendar.components.RaceWeekCard
import tmg.flashback.feature.season.presentation.calendar.components.Round
import tmg.flashback.feature.season.presentation.shared.device_time.DeviceTimePrompt
import tmg.flashback.feature.season.presentation.shared.providedby.ProvidedBy
import tmg.flashback.feature.season.presentation.shared.seasonpicker.ResultsSeasonPicker
import tmg.flashback.feature.season.presentation.tyres.TyreBottomSheet
import tmg.flashback.formula1.enums.SeasonTyres
import tmg.flashback.formula1.enums.hasEntryForSeason
import tmg.flashback.formula1.extensions.icon
import tmg.flashback.formula1.extensions.label
import tmg.flashback.infrastructure.datetime.displayDate
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.startOfWeek
import tmg.flashback.navigation.NavWeekend
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.flag.Flag
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.loading.SkeletonViewList
import tmg.flashback.ui.components.now.Now
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh
import tmg.flashback.ui.insets.compactOnly
import tmg.flashback.ui.navigation.MasterDetailPaneState
import tmg.flashback.ui.navigation.appBarMaximumHeight

private const val listAlpha = 0.6f
private val expandIcon = 20.dp

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    navigateTo: (NavKey) -> Unit,
    viewModel: CalendarScreenViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

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
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        uiState = uiState.value,
        refresh = viewModel::refresh,
        goToWeekend = {
            navigateTo(
                NavWeekend(
                    season = it.model.season,
                    round = it.model.round,
                    raceName = it.model.raceName
                )
            )
        },
        expandGroupedRaces = viewModel::clickGroupedRaces
    )
}

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: CalendarScreenState,
    refresh: () -> Unit,
    expandGroupedRaces: () -> Unit,
    goToWeekend: (CalendarItem.RaceWeek) -> Unit,
) {
    ScreenView(screenName = "Calendar", updateKey = uiState.season, args = mapOf(
        analyticsSeason to uiState.season.toString()
    ))

    val now = LocalDate.now()
    val eventsRef = remember(now.toEpochDays()) { LocalDate.now() }

    SwipeRefresh(
        isLoading = uiState.isLoading,
        onRefresh = refresh
    ) {
        LazyColumn(
            contentPadding = paddingValues,
            content = {
                item(key = "header") {
                    Header(
                        modifier = Modifier.animateItem(),
                        content = {
                            ResultsSeasonPicker(subtitle = null)
                        },
                        action = when (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                            true -> HeaderAction.MENU
                            false -> null
                        },
                        actionUpClicked = actionUpClicked,
                        overrideIcons = {
                            Refresh(onClick = refresh)
                            Tyres(uiState.season)
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
                                vertical = AppTheme.dimens.xsmall,
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
                                itemClicked = { goToWeekend(it) },
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
private fun CollapsableList(
    model: CalendarItem.GroupedCompletedRaces,
    itemClicked: (CalendarItem.GroupedCompletedRaces) -> Unit,
    modifier: Modifier = Modifier
) {
    val contentDescription = stringResource(resource = string.ab_collapsed_section,
        model.first.raceName,
        model.first.round,
        model.last?.raceName ?: model.first.raceName,
        model.last?.round ?: model.first.round
    )
    Row(modifier = modifier
        .clickable { itemClicked(model) }
        .semantics(mergeDescendants = true) { }
        .clearAndSetSemantics { this.stateDescription = contentDescription }
        .padding(
            start = AppTheme.dimens.xsmall,
            end = AppTheme.dimens.small,
            top = AppTheme.dimens.xsmall,
            bottom = AppTheme.dimens.xsmall
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Expand()

        Row(
            modifier = modifier
                .weight(1f)
                .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
                .padding(
                    horizontal = AppTheme.dimens.small,
                    vertical = AppTheme.dimens.small
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(Modifier.weight(1f)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Flag(
                        iso = model.first.countryISO,
                        nationality = null,
                        modifier = Modifier.size(20.dp)
                    )
                    TextBody1(
                        modifier = Modifier
                            .padding(horizontal = AppTheme.dimens.small)
                            .weight(1f),
                        bold = true,
                        text = model.first.raceName
                    )
                    Round(model.first.round)
                }
                if (model.last != null) {
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Flag(
                            iso = model.last.countryISO,
                            nationality = null,
                            modifier = Modifier.size(20.dp)
                        )
                        TextBody1(
                            modifier = Modifier
                                .padding(horizontal = AppTheme.dimens.small)
                                .weight(1f),
                            bold = true,
                            text = model.last.raceName
                        )
                        Round(model.last.round)
                    }
                }
            }
        }

        Expand()
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
private fun Expand(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxHeight()) {
        Icon(
            painter = painterResource(resource = Res.drawable.ic_collapsible_icon_top),
            contentDescription = null,
            modifier = Modifier.size(expandIcon),
            tint = AppTheme.colors.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))
        Icon(
            painter = painterResource(resource = Res.drawable.ic_collapsible_icon_bottom),
            contentDescription = null,
            modifier = Modifier.size(expandIcon),
            tint = AppTheme.colors.onSurfaceVariant
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

@Composable
private fun Tyres(
    season: Int
) {
    val seasonTyres = remember { mutableStateOf<Int?>(null) }
    if (SeasonTyres.hasEntryForSeason(season)) {
        IconButton(
            onClick = {
                seasonTyres.value = season
            },
            content = {
                Icon(
                    painter = painterResource(resource = drawable.ic_tyre),
                    contentDescription = stringResource(resource = string.tyres_label),
                    tint = AppTheme.colors.onSurface
                )
            }
        )
        if (seasonTyres.value != null) {
            TyreBottomSheet(
                season = season,
                dismissed = { seasonTyres.value = null }
            )
        }
    }
}