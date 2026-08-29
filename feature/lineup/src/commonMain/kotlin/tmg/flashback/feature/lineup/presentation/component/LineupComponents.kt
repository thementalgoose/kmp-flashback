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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
internal fun SeasonList(
    seasons: List<Int>,
    modifier: Modifier = Modifier
) {
    val edgePadding = driverIconSize + AppTheme.dimens.small
    Row(
        modifier = modifier
            .padding(start = edgePadding)
    ) {
        for (season in seasons) {
            TextTitle(
                text = season.toString(),
                modifier = Modifier.width(yearWidth)
            )
        }
    }
}

@Composable
internal fun ConstructorItem(
    constructorLineup: ConstructorLineup,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TextTitle(constructorLineup.constructor.name)
        for ((driver, contracts) in constructorLineup.contractBars) {
            DriverItem(
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
                ConstructorItem(lineup)
            }
        }
    }
}