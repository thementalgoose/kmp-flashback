package tmg.flashback.highlights.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider

@Composable
fun HighlightBanner(
    modifier: Modifier = Modifier,
    viewModel: HighlightsViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    HighlightBanner(
        modifier = modifier,
        uiState = uiState.value
    )
}

@Composable
private fun HighlightBanner(
    modifier: Modifier = Modifier,
    uiState: HighlightsUiState
) {

}

@Composable
@Preview
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        HighlightBanner(
            uiState = HighlightsUiState(
                news = fakeNewsList,
                show = true
            )
        )
    }
}

@Composable
@Preview
private fun PreviewHidden(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        HighlightBanner(
            uiState = HighlightsUiState(
                news = fakeNewsList,
                show = false
            )
        )
    }
}

private val fakeNewsList = listOf(
    HighlightNewsItem(
        message = "Some kind of breaking news has happened",
        link = "https://www.google.com",
        image = null,
        date = LocalDate.now()
    ),
    HighlightNewsItem(
        message = "Some kind of breaking news has happened",
        link = "https://www.google.com",
        image = "https://image.com",
        date = LocalDate.now()
    )
)