package tmg.flashback.feature.season.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import flashback.feature.season.generated.resources.ic_collapsible_icon_bottom
import flashback.feature.season.generated.resources.ic_collapsible_icon_top
import flashback.feature.season.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_collapsed_section
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.feature.season.presentation.calendar.components.RaceWeekCard
import tmg.flashback.feature.season.presentation.calendar.components.Round
import tmg.flashback.feature.season.presentation.shared.providedby.ProvidedBy
import tmg.flashback.feature.season.presentation.shared.seasonpicker.SeasonPicker
import tmg.flashback.formula1.extensions.icon
import tmg.flashback.formula1.extensions.label
import tmg.flashback.infrastructure.datetime.dateFormatDMMM
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.startOfWeek
import tmg.flashback.style.AppTheme
import tmg.flashback.style.buttons.ButtonSecondary
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.ui.components.fade.Fade
import tmg.flashback.ui.components.flag.Flag
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.loading.SkeletonViewList
import tmg.flashback.ui.components.now.Now
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

private const val listAlpha = 0.6f
private val expandIcon = 20.dp

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: CalendarScreenState,
    refresh: () -> Unit,
    itemClicked: (CalendarItem) -> Unit,
) {
    SwipeRefresh(
        isLoading = uiState.isLoading,
        onRefresh = refresh
    ) {
        LazyColumn(
            contentPadding = paddingValues,
            content = {
                item(key = "header") {
                    Header(
                        content = {
                            SeasonPicker(subtitle = null)
                        },
                        action = when (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                            true -> HeaderAction.MENU
                            false -> null
                        },
                        actionUpClicked = actionUpClicked,
                        overrideIcons = {

                        }
                    )
                }

//                item(key = "info") {
//                    DashboardQuickLinks(season = uiState.season)
//                }

                if (uiState.items.isNullOrEmpty()) {
                    if (uiState.isLoading) {
                        item(key = "loading") {
                            SkeletonViewList()
                        }
                    }
                }

                items(uiState.items ?: emptyList(), key = { it.key }) { item ->
                    when (item) {
                        is CalendarItem.RaceWeek -> {
                            RaceWeekCard(
                                model = item,
                                itemClicked = itemClicked
                            )
                        }

                        is CalendarItem.Event -> {
                            Event(event = item)
                        }

                        is CalendarItem.GroupedCompletedRaces -> {
                            Spacer(Modifier.height(AppTheme.dimens.xsmall))
                            CollapsableList(
                                model = item,
                                itemClicked = itemClicked
                            )
                            Spacer(Modifier.height(AppTheme.dimens.xsmall))
                        }

                        is CalendarItem.EmptyWeek -> {
                            EmptyWeek(model = item)
                        }
                    }
                }

                item(key = "footer") {
                    ProvidedBy()
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
            horizontal = AppTheme.dimens.xsmall,
            vertical = AppTheme.dimens.xsmall
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
    model: CalendarItem.EmptyWeek
) {
    Box(
        modifier = Modifier
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
    event: CalendarItem.Event
) {
    Row(modifier = Modifier
        .alpha(if (event.date <= LocalDate.now()) 1f else listAlpha)
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
            text = event.event.date.format(dateFormatDMMM),
        )
    }
}