package tmg.flashback.widgets.upnext.presentation.layout

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.text.TextAlign
import tmg.flashback.feature.widget_upnext.R
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.modifiers.surface
import tmg.flashback.widgets.upnext.presentation.style.preview.Preview1x1
import tmg.flashback.widgets.upnext.presentation.style.preview.Preview2x1
import tmg.flashback.widgets.upnext.presentation.style.preview.Preview4x1
import tmg.flashback.widgets.upnext.presentation.style.preview.Preview4x2
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody
import tmg.flashback.widgets.upnext.presentation.style.text.TextTitle

@Composable
internal fun NoRace(
    context: Context,
    compact: Boolean = false,
    modifier: GlanceModifier = GlanceModifier
) {
    if (compact) {
        Box(
            modifier = modifier.surface(GlanceTheme.colors.background.getColor(context)),
            contentAlignment = Alignment.Center
        ) {
            TextTitle(
                modifier = GlanceModifier.padding(
                    top = 4.dp,
                    bottom = 4.dp
                ),
                textAlign = TextAlign.Center,
                text = context.getString(R.string.widget_up_next_nothing_title_compact),
                color = GlanceTheme.colors.onBackground.getColor(context)
            )
        }
    } else {
        Column(
            modifier = modifier.surface(GlanceTheme.colors.background.getColor(context)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = GlanceModifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(GlanceModifier.fillMaxWidth()) {
                    TextTitle(
                        modifier = GlanceModifier.padding(
                            top = 4.dp,
                            bottom = 4.dp
                        ),
                        text = context.getString(R.string.widget_up_next_nothing_title),
                        color = GlanceTheme.colors.onBackground.getColor(context)
                    )
                }

                TextBody(
                    text = context.getString(R.string.widget_up_next_nothing_subtitle)
                )
            }
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview2x1
@Preview4x1
@Preview4x2
@Composable
private fun Preview() {
    WidgetThemePreview {
        NoRace(
            context = LocalContext.current,
            compact = false
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview1x1
@Composable
private fun PreviewCompact() {
    WidgetThemePreview {
        NoRace(
            context = LocalContext.current,
            compact = true
        )
    }
}