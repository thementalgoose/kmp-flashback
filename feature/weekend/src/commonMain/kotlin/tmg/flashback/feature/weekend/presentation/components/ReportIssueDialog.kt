package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.report_issue_dialog_cancel
import flashback.presentation.localisation.generated.resources.report_issue_dialog_confirm
import flashback.presentation.localisation.generated.resources.report_issue_dialog_message
import flashback.presentation.localisation.generated.resources.report_issue_dialog_title
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.buttons.ButtonSecondary
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportIssueDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        content = {
            ReportIssueDialogContent(
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
        }
    )
}

@Composable
private fun ReportIssueDialogContent(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
            .background(AppTheme.colors.surface)
            .padding(AppTheme.dimens.medium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.nsmall)
    ) {
        TextTitle(
            text = stringResource(string.report_issue_dialog_title),
            bold = true
        )
        TextBody2(
            text = stringResource(string.report_issue_dialog_message)
        )
        ButtonPrimary(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(string.report_issue_dialog_confirm),
            onClick = onConfirm
        )
        ButtonSecondary(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(string.report_issue_dialog_cancel),
            onClick = onDismiss
        )
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        ReportIssueDialogContent(
            onConfirm = { },
            onDismiss = { }
        )
    }
}
