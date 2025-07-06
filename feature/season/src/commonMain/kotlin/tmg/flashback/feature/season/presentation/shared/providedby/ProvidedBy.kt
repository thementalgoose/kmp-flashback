package tmg.flashback.feature.season.presentation.shared.providedby

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2

@Composable
fun ProvidedBy(
    modifier: Modifier = Modifier,
    viewModel: ProvidedByViewModel = koinViewModel()
) {
    val message = viewModel.message ?: return

    ProvidedBy(
        modifier = modifier,
        title = message,
        onClick = { }
    )
}

@Composable
private fun ProvidedBy(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextBody2(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            ),
        text = title,
        bold = true
    )
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        ProvidedBy(
            title = "Data is provided with <3 by the Flashback team",
            onClick = {}
        )
    }
}