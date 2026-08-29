package tmg.flashback.feature.lineup.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tmg.flashback.feature.lineup.presentation.ConstructorBar
import tmg.flashback.feature.lineup.presentation.ConstructorLineup
import tmg.flashback.feature.lineup.presentation.fakeUiState
import tmg.flashback.formula1.model.Driver
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.driver.DriverIcon
import tmg.flashback.ui.components.driver.DriverName
import tmg.flashback.ui.components.driver.driverIconSize

private val yearWidth: Dp = 80.dp
private val yearHeight: Dp = 32.dp

private val constructorLineHeight: Dp = 2.dp
private val startPadding: Dp
    get() = driverIconSize + AppTheme.dimens.small

@Composable
internal fun SeasonList(
    seasons: List<Int>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(start = startPadding)
    ) {
        for (season in seasons) {
            TextTitle(
                bold = true,
                text = season.toString(),
                modifier = Modifier
                    .width(yearWidth)
                    .padding(horizontal = AppTheme.dimens.small)
            )
        }
    }
}

@Composable
internal fun ConstructorItem(
    seasons: List<Int>,
    constructorLineup: ConstructorLineup,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TextTitle(
            text = constructorLineup.constructor.name,
            bold = true
        )
        Row {
            Box(Modifier
                .width(startPadding)
                .height(constructorLineHeight))
            Box(Modifier
                .width(yearWidth * seasons.size)
                .height(constructorLineHeight))
        }
        for ((driver, contracts) in constructorLineup.contractBars) {
            DriverItem(
                modifier = Modifier.padding(top = AppTheme.dimens.small),
                constructorColor = constructorLineup.constructor.colour,
                driver = driver,
                contracts = contracts
            )
        }
    }
}

@Composable
private fun DriverItem(
    constructorColor: Color,
    driver: Driver,
    contracts: List<ConstructorBar>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {
        DriverIcon(
            photoUrl = driver.photoUrl,
            constructorColor = constructorColor
        )
        Column(
            modifier = Modifier
                .height(IntrinsicSize.Min),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            DriverName(driver.firstName, driver.lastName)
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                for (contract in contracts) {
                    if (contract.signedup) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .heightIn(min = yearHeight)
                                .width(yearWidth * contract.seasons)
                                .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
                                .background(constructorColor)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .width(yearWidth * contract.seasons)
                                .heightIn(min = yearHeight)
                        )
                    }
                }
            }
        }
    }
}

@PreviewTheme
@Composable
private fun PreviewDriver() {
    ApplicationThemePreview {
        Column {
            SeasonList(fakeUiState.seasons)
            for (lineup in fakeUiState.rows) {
                ConstructorItem(
                    seasons = fakeUiState.seasons,
                    constructorLineup = lineup
                )
            }
        }
    }
}