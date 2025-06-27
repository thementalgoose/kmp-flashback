package tmg.flashback.feature.weekend.presentation.data.qualifying

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_result_qualifying_overview
import flashback.presentation.localisation.generated.resources.ab_result_qualifying_overview_dnq
import flashback.presentation.localisation.generated.resources.qualifying_header_q1
import flashback.presentation.localisation.generated.resources.qualifying_header_q2
import flashback.presentation.localisation.generated.resources.qualifying_header_q3
import flashback.presentation.localisation.generated.resources.qualifying_penalty
import flashback.presentation.localisation.generated.resources.qualifying_penalty_starts
import flashback.presentation.localisation.generated.resources.qualifying_penalty_starts_sprint
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.feature.weekend.presentation.components.Position
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.LapTime
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.badge.Badge
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextSection
import tmg.flashback.ui.components.driver.DriverName
import tmg.flashback.ui.components.edgeBar


private val lapTimeWidth: Dp = 64.dp

@Composable
internal fun QualifyingHeader(
    modifier: Modifier = Modifier,
    showQ1: Boolean = true,
    showQ2: Boolean = true,
    showQ3: Boolean = true,
) {
    Row(modifier = modifier
        .padding(vertical = AppTheme.dimens.small)
    ) {
        Box(Modifier.weight(1f))

        if (showQ1) {
            TextSection(
                modifier = Modifier.width(lapTimeWidth),
                text = stringResource(resource = string.qualifying_header_q1),
                textAlign = TextAlign.Center
            )
        }
        if (showQ2) {
            TextSection(
                modifier = Modifier.width(lapTimeWidth),
                text = stringResource(resource = string.qualifying_header_q2),
                textAlign = TextAlign.Center
            )
        }
        if (showQ3) {
            TextSection(
                modifier = Modifier.width(lapTimeWidth),
                text = stringResource(resource = string.qualifying_header_q3),
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.width(AppTheme.dimens.medium))
    }
}

@Composable
internal fun QualifyingResult(
    model: QualifyingModel.Q1Q2Q3,
    driverClicked: (Driver) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(Modifier.fillMaxWidth()) {
        Row(modifier = modifier
            .height(IntrinsicSize.Min)
        ) {
            Column(Modifier.weight(1f)) {
                DriverLabel(
                    modifier = Modifier
                        .clickable(onClick = { driverClicked(model.driver.driver) }),
                    driver = model.driver,
                    qualifyingPosition = model.qualified,
                    grid = model.grid,
                    sprintQualifyingGrid = model.sprintRaceGrid
                )
            }
            Time(
                modifier = Modifier.fillMaxHeight(),
                laptime = model.q1?.lapTime,
                contentDescription = string.qualifying_header_q1
            )
            Time(
                modifier = Modifier.fillMaxHeight(),
                laptime = model.q2?.lapTime,
                contentDescription = string.qualifying_header_q2
            )
            Time(
                modifier = Modifier.fillMaxHeight(),
                laptime = model.q3?.lapTime,
                contentDescription = string.qualifying_header_q3
            )
            Spacer(Modifier.width(AppTheme.dimens.medium))
        }
    }
}

@Composable
internal fun QualifyingResult(
    model: QualifyingModel.Q1Q2,
    driverClicked: (Driver) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .height(IntrinsicSize.Min)
    ) {
        DriverLabel(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = { driverClicked(model.driver.driver) }),
            driver = model.driver,
            qualifyingPosition = model.qualified,
            grid = null,
            sprintQualifyingGrid = null
        )
        Time(
            modifier = Modifier.fillMaxHeight(),
            laptime = model.q1?.lapTime,
            contentDescription = string.qualifying_header_q1
        )
        Time(
            modifier = Modifier.fillMaxHeight(),
            laptime = model.q2?.lapTime,
            contentDescription = string.qualifying_header_q2
        )
        Spacer(Modifier.width(AppTheme.dimens.medium))
    }
}

@Composable
internal fun QualifyingResult(
    model: QualifyingModel.Q1,
    driverClicked: (Driver) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .height(IntrinsicSize.Min)
    ) {
        DriverLabel(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = { driverClicked(model.driver.driver) }),
            driver = model.driver,
            qualifyingPosition = model.qualified,
            grid = null,
            sprintQualifyingGrid = null
        )
        Time(
            modifier = Modifier.fillMaxHeight(),
            laptime = model.q1?.lapTime,
            contentDescription = string.qualifying_header_q1
        )
        Spacer(Modifier.width(AppTheme.dimens.medium))
    }
}


@Composable
private fun DriverLabel(
    driver: DriverEntry,
    qualifyingPosition: Int?,
    grid: Int?,
    sprintQualifyingGrid: Int?,
    modifier: Modifier = Modifier
) {
    val contentDescription = if (qualifyingPosition == null) {
        stringResource(
            string.ab_result_qualifying_overview_dnq,
            driver.driver.name,
            driver.constructor.name
        )
    } else {
        stringResource(
            string.ab_result_qualifying_overview,
            driver.driver.name,
            driver.driver.name,
            qualifyingPosition.toString()
        )
    }
    Row(modifier = modifier
        .edgeBar(driver.constructor.colour)
        .semantics(mergeDescendants = true) { }
        .clearAndSetSemantics { this.contentDescription = contentDescription }
    ) {
        Position(label = qualifyingPosition?.toString() ?: "-")
        Column(Modifier.fillMaxWidth()) {
            DriverName(
                firstName = driver.driver.firstName,
                lastName = driver.driver.lastName,
                modifier = Modifier.padding(top = AppTheme.dimens.xsmall)
            )
            TextBody2(
                text = driver.constructor.name,
                modifier = Modifier.padding(vertical = AppTheme.dimens.xsmall)
            )

            if (qualifyingPosition != null) {
                if (sprintQualifyingGrid != null) {
                    if (sprintQualifyingGrid > qualifyingPosition) {
                        BadgeView(
                            modifier = Modifier.padding(bottom = AppTheme.dimens.xsmall),
                            model = Badge(stringResource(resource = string.qualifying_penalty_starts_sprint, sprintQualifyingGrid.toString()))
                        )
                    } else if (sprintQualifyingGrid == 0) {
                        BadgeView(
                            modifier = Modifier.padding(bottom = AppTheme.dimens.xsmall),
                            model = Badge(stringResource(resource = string.qualifying_penalty, sprintQualifyingGrid.toString()))
                        )
                    }
                } else if (grid != null) {
                    if (grid > qualifyingPosition) {
                        BadgeView(
                            modifier = Modifier.padding(bottom = AppTheme.dimens.xsmall),
                            model = Badge(stringResource(resource = string.qualifying_penalty_starts, grid.toString()))
                        )
                    } else if (grid == 0) {
                        BadgeView(
                            modifier = Modifier.padding(bottom = AppTheme.dimens.xsmall),
                            model = Badge(stringResource(resource = string.qualifying_penalty, grid.toString()))
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Time(
    laptime: LapTime?,
    contentDescription: StringResource,
    modifier: Modifier = Modifier
) {
    val description = stringResource(contentDescription)
    Box(modifier = modifier
        .width(lapTimeWidth)
        .semantics(mergeDescendants = true) {
            this.contentDescription = description
        }
    ) {
        TextBody2(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics {
                    if (laptime == null) {
                        this.contentDescription = ""
                    }
                }
                .padding(vertical = AppTheme.dimens.nsmall),
            text = laptime?.time ?: ""
        )
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Column {
            QualifyingHeader(showQ1 = true, showQ2 = false, showQ3 = false)
            QualifyingResult(QualifyingModel.Q1.preview(), driverClicked = { })

            QualifyingHeader(showQ1 = true, showQ2 = true, showQ3 = false)
            QualifyingResult(QualifyingModel.Q1Q2.preview(), driverClicked = { })

            QualifyingHeader(showQ1 = true, showQ2 = true, showQ3 = true)
            QualifyingResult(QualifyingModel.Q1Q2Q3.preview(), driverClicked = { })
        }
    }
}