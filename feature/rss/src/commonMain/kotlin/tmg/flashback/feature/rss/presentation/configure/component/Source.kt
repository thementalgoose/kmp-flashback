package tmg.flashback.feature.rss.presentation.configure.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_rss_contact_link
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.presentation.configure.ConfiguredSupportedSource
import tmg.flashback.infrastructure.extensions.toColourInt
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.buttons.ButtonSecondary
import tmg.flashback.style.input.InputSwitch
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle

private val badgeSize: Dp = 42.dp

@Composable
internal fun Source(
    model: ConfiguredSupportedSource,
    sourceAdded: (source: SupportedSource) -> Unit,
    sourceRemoved: (source: SupportedSource) -> Unit,
    contactLink: (link: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable(onClick = {
            if (model.isEnabled) {
                sourceRemoved(model.article)
            } else {
                sourceAdded(model.article)
            }
        })
        .padding(
            vertical = AppTheme.dimens.small,
            horizontal = AppTheme.dimens.medium
        )
    ) {
        SourceBadge(
            title = model.article.sourceShort,
            textColour = model.article.textColour.toColourInt()?.let { Color(it) } ?: Color.White,
            colour = model.article.colour.toColourInt()?.let { Color(it) } ?: AppTheme.colors.primary
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = AppTheme.dimens.medium)
        ) {
            TextTitle(
                text = model.article.source
            )
            TextBody2(
                text = model.article.rssLink,
                modifier = Modifier.padding(top = 4.dp)
            )
            ButtonSecondary(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(resource = string.settings_rss_contact_link),
                onClick = {
                    contactLink(model.article.contactLink)
                }
            )
        }
        InputSwitch(isChecked = model.isEnabled)
    }
}


@Composable
private fun SourceBadge(
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


@Preview
@Composable
private fun PreviewSelected(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Source(
            model = ConfiguredSupportedSource(fakeSource, true),
            sourceAdded = { },
            sourceRemoved = { },
            contactLink = { }
        )
    }
}


@Preview
@Composable
private fun PreviewNotSelected(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Source(
            model = ConfiguredSupportedSource(fakeSource, false),
            sourceAdded = { },
            sourceRemoved = { },
            contactLink = { }
        )
    }
}

private val fakeSource: SupportedSource = SupportedSource(
    rssLink = "https://www.url.com/f1/rss.xml",
    sourceShort = "URL",
    source = "https://www.url.com/",
    colour = "#928284",
    textColour = "#efefef",
    title = "Motorsport API",
    contactLink = "https://www.url.com/contact",
)