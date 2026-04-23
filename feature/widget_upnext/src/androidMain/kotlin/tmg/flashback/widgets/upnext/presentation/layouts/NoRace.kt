package tmg.flashback.widgets.upnext.presentation.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.LocalSize
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.TextAlign
import tmg.flashback.feature.widget_upnext.R
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.preview.PreviewAllSizes
import tmg.flashback.widgets.upnext.presentation.style.preview.PreviewPixel
import tmg.flashback.widgets.upnext.presentation.style.text.TextFeature
import tmg.flashback.widgets.upnext.presentation.style.text.TextTitle
import androidx.compose.ui.res.stringResource
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody1

private const val compactCutoffWidth = 250
private const val compactCutoffHeight = 130

@Composable
internal fun NoRace(
    modifier: GlanceModifier = GlanceModifier,
    titleCompact: String = stringResource(R.string.widget_up_next_nothing_title_compact),
    title: String = stringResource(R.string.widget_up_next_nothing_title),
    subtitle: String = stringResource(R.string.widget_up_next_nothing_subtitle)
) {
    val size = LocalSize.current
    if (size.width >= compactCutoffWidth.dp && size.height >= compactCutoffHeight.dp) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = GlanceModifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(GlanceModifier.fillMaxWidth()) {
                    TextTitle(
                        weight = FontWeight.Bold,
                        modifier = GlanceModifier.padding(
                            top = 4.dp,
                            bottom = 4.dp
                        ),
                        text = title,
                    )
                }

                TextBody1(
                    text = subtitle
                )
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TextTitle(
                modifier = GlanceModifier.padding(
                    top = 4.dp,
                    bottom = 4.dp
                ),
                textAlign = TextAlign.Center,
                text = titleCompact,
            )
        }
    }
}

@ExperimentalGlancePreviewApi
@Composable
@Preview(widthDp = compactCutoffWidth, heightDp = compactCutoffHeight)
@Preview(widthDp = 64, heightDp = 64)
private fun Preview() {
    WidgetThemePreview {
        NoRace(
            titleCompact = "N/A",
            title = "Nothing coming up",
            subtitle = "We will get this information as soon as it\\'s available. Click here to check for updates"
        )
    }
}