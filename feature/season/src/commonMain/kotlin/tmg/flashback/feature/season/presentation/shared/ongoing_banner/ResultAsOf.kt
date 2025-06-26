package tmg.flashback.feature.season.presentation.shared.ongoing_banner

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.results_accurate_for
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2

@Composable
fun ResultAsOf(
    grandPrixName: String,
    round: Int,
    modifier: Modifier = Modifier.padding(
        horizontal = AppTheme.dimens.medium,
        vertical = AppTheme.dimens.xsmall
    )
) {
    TextBody2(
        text = stringResource(string.results_accurate_for, grandPrixName, round),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        ResultAsOf(
            grandPrixName = "The Flashback Grand Prix",
            round = 10,
        )
    }
}