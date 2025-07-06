package tmg.flashback.feature.rss.presentation.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import flashback.feature.rss.generated.resources.Res
import flashback.feature.rss.generated.resources.ic_rss_icon_no_sources
import flashback.feature.rss.generated.resources.ic_rss_settings
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_rss_settings
import flashback.presentation.localisation.generated.resources.home_last_updated
import flashback.presentation.localisation.generated.resources.rss_no_articles
import flashback.presentation.localisation.generated.resources.title_rss
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.feature.rss.models.Article
import tmg.flashback.feature.rss.models.ArticleSource
import tmg.flashback.infrastructure.datetime.dateTimeFormatHHmmAtDMMM
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.extensions.toColourInt
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextCaption
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

private val badgeSize: Dp = 40.dp

@Composable
fun RSSScreen(
    actionUpClicked: () -> Unit,
    refresh: () -> Unit,
    paddingValues: PaddingValues,
    uiState: RssFeedUiState,
    showMenu: Boolean,
    itemClicked: (Article) -> Unit,
    configureSources: () -> Unit,
) {
    SwipeRefresh(
        isLoading = (uiState as? RssFeedUiState.Data)?.isLoading == true,
        onRefresh = refresh,
        modifier = Modifier.fillMaxSize(),
        content = {
            LazyColumn(
                contentPadding = paddingValues,
                content = {
                    item("header") {
                        Header(
                            text = stringResource(resource = string.title_rss),
                            action = HeaderAction.MENU.takeIf { showMenu },
                            overrideIcons = {
                                IconButton(onClick = configureSources) {
                                    Icon(
                                        painter = painterResource(resource = Res.drawable.ic_rss_settings),
                                        contentDescription = stringResource(resource = string.ab_rss_settings),
                                        tint = AppTheme.colors.onSurface
                                    )
                                }
                            },
                            actionUpClicked = actionUpClicked
                        )
                    }
                    when (uiState) {
                        is RssFeedUiState.NoNetwork -> {

                        }
                        is RssFeedUiState.Data -> {
                            if (!uiState.hasSources) {
                                item(key = "sources-disabled") {
                                    SourcesDisabled(sourceClicked = configureSources)
                                }
                            } else if (uiState.lastUpdated != null) {
                                item(key = "updated") {
                                    TextBody2(
                                        text = stringResource(resource = string.home_last_updated, uiState.lastUpdated),
                                        modifier = Modifier.padding(
                                            horizontal = AppTheme.dimens.medium,
                                            vertical = AppTheme.dimens.xsmall
                                        )
                                    )
                                }
                            }
                            item("debug") {
                                TextBody2("Rss items ${uiState.rssItems}")
                            }
                            items(uiState.rssItems, key = { it.link }) {
                                Item(it, itemClicked)
                            }
                        }
                    }
                }
            )
        }
    )
}

@Composable
private fun Item(
    model: Article,
    clickItem: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .clickable(onClick = {
            clickItem(model)
        })
        .padding(
            vertical = AppTheme.dimens.nsmall,
            horizontal = AppTheme.dimens.medium
        )
    ) {
        SourceBadge(
            title = model.source.shortSource
                ?: model.source.source.substring(0..2),
            textColour = Color(model.source.textColor.toColourInt()),
            colour = Color(model.source.colour.toColourInt())
        )
        Spacer(Modifier.width(AppTheme.dimens.medium))
        Column(modifier = Modifier.weight(1f)) {
            TextBody1(
                text = model.title,
                bold = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            if (model.description != null) {
                TextBody2(
                    text = model.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )
            }
            TextCaption(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = "${model.source.source} - ${model.date?.format(dateTimeFormatHHmmAtDMMM)}",
            )
        }
    }
}

@Composable
internal fun SourceBadge(
    title: String,
    textColour: Color,
    colour: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .size(badgeSize)
        .clip(CircleShape)
        .background(colour)
    ) {
        Text(
            text = title,
            maxLines = 1,
            style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.Center),
            color = textColour
        )
    }
}

@Composable
private fun SourcesDisabled(
    sourceClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .clickable(onClick = sourceClicked)
        .padding(
            vertical = AppTheme.dimens.medium,
            horizontal = AppTheme.dimens.medium
        )
    ) {
        Icon(
            painter = painterResource(resource = Res.drawable.ic_rss_icon_no_sources),
            contentDescription = null,
            tint = AppTheme.colors.onSurface,
            modifier = Modifier.size(36.dp)
        )
        Spacer(Modifier.width(16.dp))
        TextBody1(
            text = stringResource(resource = string.rss_no_articles)
        )
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        RSSScreen(
            paddingValues = PaddingValues.Absolute(),
            uiState = RssFeedUiState.Data(
                rssItems = listOf(fakeArticle),
                isLoading = false,
            ),
            itemClicked = {},
            configureSources = {},
            actionUpClicked = {},
            showMenu = true,
            refresh = { }
        )
    }
}

private val fakeArticle: Article get() = Article(
    id = "id",
    title = "Title",
    description = "This is a long description of the content of the website for the reader to confume",
    link = "https://www.link.com",
    date = LocalDateTime.now(),
    source = fakeArticleSource,
)

private val fakeArticleSource: ArticleSource get() = ArticleSource(
    title = "title",
    colour = "#123123",
    textColor = "#ffffff",
    rssLink = "https://www.rsslink.com/link",
    source = "https://www.rsslink.com",
    shortSource = "SO",
    contactLink = "https://www.rsslink.com/contact",
)