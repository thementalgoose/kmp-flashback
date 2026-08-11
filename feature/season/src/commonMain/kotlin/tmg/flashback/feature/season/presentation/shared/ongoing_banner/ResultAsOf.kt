package tmg.flashback.feature.season.presentation.shared.ongoing_banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.results_accurate_for
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody2

@Composable
fun ResultAsOf(
    grandPrixName: String,
    round: Int,
    modifier: Modifier = Modifier
) {
    TextBody2(
        text = stringResource(string.results_accurate_for, grandPrixName, round),
        modifier = modifier,
    )
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        ResultAsOf(
            grandPrixName = "The Flashback Grand Prix",
            round = 10,
        )
    }
}