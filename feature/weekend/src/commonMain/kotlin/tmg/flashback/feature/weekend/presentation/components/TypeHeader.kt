package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_race
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextHeadline2

@Composable
internal fun TypeHeader(
    resource: StringResource,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            )
            .clickable(
                enabled = true,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextHeadline2(
            text = stringResource(resource),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
internal fun TypeHeader(
    resource: StringResource,
    resultType: ResultType,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    selectResultType: (ResultType) -> Unit,
    isCollapsed: MutableState<Boolean>? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            )
            .clickable(
                enabled = isCollapsed != null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextHeadline2(
            modifier = Modifier,
            text = stringResource(resource)
        )
        DriverTeamSwitcher(
            modifier = Modifier
                .weight(1f)
                .padding(start = AppTheme.dimens.medium),
            isDrivers = resultType == ResultType.DRIVERS,
            driversClicked = { selectResultType(ResultType.DRIVERS) },
            teamsClicked = { selectResultType(ResultType.CONSTRUCTORS) }
        )
    }
}


@Composable
@Preview
private fun PreviewTypeHeaderRegular(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        TypeHeader(
            resource = string.nav_race
        )
    }
}

@Composable
@Preview
private fun PreviewTypeHeaderResult(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        TypeHeader(
            resource = string.nav_race,
            resultType = ResultType.DRIVERS,
            selectResultType = { }
        )
    }
}