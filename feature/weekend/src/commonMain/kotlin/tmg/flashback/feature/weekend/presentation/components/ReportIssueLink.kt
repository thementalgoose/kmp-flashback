package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.report_issue_link
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextCaption

@Composable
fun ReportIssueLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                vertical = AppTheme.dimens.small,
                horizontal = AppTheme.dimens.medium
            )
    ) {
        TextCaption(
            text = stringResource(string.report_issue_link),
            textColor = AppTheme.colors.onSurfaceVariant,
        )
    }
}

@PreviewTheme
@Composable
private fun ReportIssueLinkPreview() {
    ApplicationThemePreview {
        ReportIssueLink(
            onClick = { }
        )
    }
}
