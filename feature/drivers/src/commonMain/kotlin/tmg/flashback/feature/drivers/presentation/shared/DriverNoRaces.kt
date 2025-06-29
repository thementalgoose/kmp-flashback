package tmg.flashback.feature.drivers.presentation.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.driver_overview_no_races
import flashback.presentation.localisation.generated.resources.error_search_not_found
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextHeadline1


@Composable
internal fun DriverNoRaces(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
    ) {
        TextHeadline1(
            text = ":("
        )
        // TODO: Better translation for this!
        TextBody1(
            text = stringResource(string.driver_overview_no_races)
        )
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        DriverNotFound()
    }
}

