package tmg.flashback.feature.highlights.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_link
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DayOfWeekNames
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.highlights.domain.models.NewsItem
import tmg.flashback.infrastructure.datetime.displayDate
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextCaption
import tmg.flashback.ui.components.loading.shimmerLoading

@Composable
fun HighlightBanner(
    modifier: Modifier = Modifier,
    viewModel: HighlightsViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.refresh()
    }

    HighlightBanner(
        modifier = modifier,
        uiState = uiState.value,
        close = viewModel::hide,
        clickItem = viewModel::clickItem,
    )
}

@Composable
private fun HighlightBanner(
    modifier: Modifier = Modifier,
    uiState: HighlightsUiState,
    close: () -> Unit,
    clickItem: (String) -> Unit,
) {
    AnimatedContent(
        targetState = uiState,
        modifier = modifier,
    ) {
        when {
            !it.show -> { }
            uiState.news == null -> {
                SkeletonView(
                    modifier = Modifier.padding(
                        horizontal = AppTheme.dimens.medium,
                        vertical = AppTheme.dimens.small
                    )
                )
            }
            uiState.news.isEmpty() -> { }
            uiState.news.size == 1 -> {
                Column {
                    NewsCard(
                        newsItem = uiState.news.first(),
                        clickItem = clickItem,
                        modifier = Modifier.padding(
                            horizontal = AppTheme.dimens.medium,
                            vertical = AppTheme.dimens.small
                        )
                    )
                }
            }
            else -> {
                LazyRow(
                    contentPadding = PaddingValues(
                        horizontal = AppTheme.dimens.medium,
                        vertical = AppTheme.dimens.small
                    ),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
                ) {
                    items(uiState.news) {
                        NewsCard(
                            newsItem = it,
                            clickItem = clickItem,
                            modifier = Modifier
                                .width(300.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SkeletonView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
            .background(AppTheme.colors.tertiaryContainer)
            .shimmerLoading()
            .fillMaxWidth()
            .height(36.dp)
    )
}

@Composable
private fun NewsCard(
    newsItem: NewsItem,
    clickItem: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
            .background(AppTheme.colors.tertiaryContainer)
            .clickable(
                enabled = newsItem.link != null,
                onClick = {
                    if (newsItem.link != null) {
                        clickItem(newsItem.link)
                    }
                }
            )
            .padding(
                horizontal = AppTheme.dimens.nsmall,
                vertical = AppTheme.dimens.small
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            TextBody1(
                text = newsItem.message,
                textColor = AppTheme.colors.onTertiaryContainer
            )
            TextCaption(
                modifier = Modifier.padding(top = 3.dp),
                text = newsItem.date.displayDate(
                    weekdays = DayOfWeekNames.ENGLISH_ABBREVIATED,
                    includeYear = false
                ).uppercase(),
                textColor = AppTheme.colors.onTertiaryContainer
            )
        }
        if (newsItem.link != null) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(Res.drawable.ic_link),
                contentDescription = null,
                tint = AppTheme.colors.onTertiaryContainer,
            )
        }
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        HighlightBanner(
            uiState = HighlightsUiState(
                news = listOf(fakeNewsItem()),
                show = true
            ),
            clickItem = { },
            close = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewSkeleton() {
    ApplicationThemePreview {
        HighlightBanner(
            uiState = HighlightsUiState(
                news = null,
                show = true
            ),
            clickItem = { },
            close = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewMultiple() {
    ApplicationThemePreview {
        HighlightBanner(
            uiState = HighlightsUiState(
                news = listOf(fakeNewsItem(), fakeNewsItem()),
                show = true
            ),
            clickItem = { },
            close = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewHidden() {
    ApplicationThemePreview {
        HighlightBanner(
            uiState = HighlightsUiState(
                news = listOf(fakeNewsItem()),
                show = false
            ),
            clickItem = { },
            close = { }
        )
    }
}

private fun fakeNewsItem(): NewsItem = NewsItem(
    message = "Some kind of breaking news has happened",
    link = "https://www.google.com",
    image = null,
    date = LocalDate.now()
)