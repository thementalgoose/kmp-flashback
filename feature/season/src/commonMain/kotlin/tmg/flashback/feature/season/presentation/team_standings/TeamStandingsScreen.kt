package tmg.flashback.feature.season.presentation.team_standings


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.season_standings_constructor
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsConstructorId
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.season.presentation.shared.ongoing_banner.ResultAsOf
import tmg.flashback.feature.season.presentation.shared.providedby.ProvidedBy
import tmg.flashback.feature.season.presentation.shared.seasonpicker.SeasonPicker
import tmg.flashback.formula1.model.SeasonConstructorStandingSeason
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.driver.DriverPoints
import tmg.flashback.ui.components.edgeBar
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.loading.SkeletonViewList
import tmg.flashback.ui.components.progressbar.ProgressBar
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun TeamStandingsScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: TeamStandingsState,
    constructorClicked: (SeasonConstructorStandingSeason) -> Unit,
    refresh: () -> Unit
) {
    ScreenView(screenName = "Team Standings", updateKey = uiState.season, args = mapOf(
        analyticsSeason to uiState.season.toString()
    ))
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
                            SeasonPicker(subtitle = stringResource(resource = string.season_standings_constructor))
                        },
                        action = when (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                            true -> HeaderAction.MENU
                            false -> null
                        },
                        actionUpClicked = actionUpClicked
                    )
                }
                uiState.inProgress?.let { (raceName, round) ->
                    item(key = "banner") {
                        ResultAsOf(raceName, round)
                    }
                }
                if (uiState.standings.isEmpty()) {
                     if (uiState.isLoading) {
                        item(key = "loading") {
                            SkeletonViewList()
                        }
                    }
                }
                items(uiState.standings, key = { "constructor=${it.constructor.id}" }) {
                    ConstructorStandings(
                        model = it,
                        itemClicked = constructorClicked,
                        maxPoints = uiState.maxPoints
                    )
                }
                item(key = "footer") {
                    ProvidedBy()
                }
            }
        )
    }
}

@Composable
private fun ConstructorStandings(
    model: SeasonConstructorStandingSeason,
    itemClicked: (SeasonConstructorStandingSeason) -> Unit,
    maxPoints: Double,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .edgeBar(model.constructor.colour)
            .clickable(onClick = {
                itemClicked(model)
            }),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextTitle(
            text = model.championshipPosition?.toString() ?: "-",
            bold = true,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(36.dp)
        )
        Row(modifier = Modifier.padding(
            top = AppTheme.dimens.small,
            start = 0.dp,
            end = AppTheme.dimens.medium,
            bottom = AppTheme.dimens.small
        )) {
            Column(modifier = Modifier.weight(3f)) {
                TextTitle(
                    text = model.constructor.name,
                    bold = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(2.dp))
                model.drivers.forEach {
                    DriverPoints(
                        name = it.driver.name,
                        nationality = it.driver.nationality,
                        nationalityISO = it.driver.nationalityISO,
                        points = it.points
                    )
                }
            }
            Spacer(Modifier.width(AppTheme.dimens.small))
            ProgressBar(
                modifier = Modifier
                    .weight(2f)
                    .height(48.dp)
                    .fillMaxHeight(),
                points = model.points,
                maxPoints = maxPoints,
                barColor = model.constructor.colour
            )
        }
    }
}