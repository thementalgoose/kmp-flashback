package tmg.flashback.widgets.upnext.presentation.layout

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import tmg.flashback.feature.widget_upnext.R
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.modifiers.surface
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody
import tmg.flashback.widgets.upnext.presentation.UpNextWidgetRefreshWidget
import tmg.flashback.widgets.upnext.presentation.components.CountryIcon
import tmg.flashback.widgets.upnext.presentation.components.FeatureDate
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace

private const val raceSmallWidth = 260
private const val raceSmallHeight = 90
private const val raceFeatureWidth = 260
private const val raceFeatureHeight = 108

private val raceTextSmall = 22.sp
private val raceTextLarge = 30.sp

@Composable
internal fun RaceName(
    context: Context,
    overviewRace: OverviewRace,
    showRefresh: Boolean = false,
    modifier: GlanceModifier = GlanceModifier
) {
    Race(
        context = context,
        overviewRace = overviewRace,
        timeSize = raceTextSmall,
        showRace = false,
        showRefresh = showRefresh,
        modifier = modifier,
    )
}

@Composable
internal fun RaceSmall(
    context: Context,
    overviewRace: OverviewRace,
    showRefresh: Boolean = false,
    modifier: GlanceModifier = GlanceModifier
) {
    Race(
        context = context,
        overviewRace = overviewRace,
        showRace = true,
        timeSize = raceTextSmall,
        showRefresh = showRefresh,
        modifier = modifier,
    )
}

@Composable
internal fun RaceLarge(
    context: Context,
    overviewRace: OverviewRace,
    showRefresh: Boolean = false,
    modifier: GlanceModifier = GlanceModifier,
) {
    Race(
        context = context,
        overviewRace = overviewRace,
        showRace = true,
        timeSize = raceTextLarge,
        showRefresh = showRefresh,
        modifier = modifier,
    )
}

@Composable
private fun Race(
    context: Context,
    overviewRace: OverviewRace,
    timeSize: TextUnit,
    showRace: Boolean,
    showRefresh: Boolean,
    modifier: GlanceModifier,
) {
    Column(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = GlanceModifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CountryIcon(
                    context = context,
                    country = overviewRace.country,
                    countryISO = overviewRace.countryISO
                )
                TextBody(
                    modifier = GlanceModifier
                        .defaultWeight()
                        .padding(
                            top = 4.dp,
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 2.dp
                        ),
                    text = overviewRace.raceName,
                    color = GlanceTheme.colors.onBackground.getColor(context)
                )
                if (showRefresh) {
                    Image(
                        modifier = GlanceModifier.clickable(actionRunCallback<UpNextWidgetRefreshWidget>()),
                        provider = ImageProvider(R.drawable.ic_widget_refresh),
                        contentDescription = null,
                    )
                }
            }

            if (showRace) {
                FeatureDate(
                    featureTextSize = timeSize,
                    overviewRace = overviewRace,
                    context = context
                )
            }
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceFeatureWidth, heightDp = raceFeatureHeight)
@Composable
private fun PreviewLargeRefresh() {
    WidgetThemePreview {
        RaceLarge(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showRefresh = true
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceFeatureWidth, heightDp = raceFeatureHeight)
@Composable
private fun PreviewLarge() {
    WidgetThemePreview {
        RaceLarge(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showRefresh = false
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceSmallWidth, heightDp = raceSmallHeight)
@Composable
private fun PreviewSmallRefresh() {
    WidgetThemePreview {
        RaceSmall(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showRefresh = false
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceSmallWidth, heightDp = raceSmallHeight)
@Composable
private fun PreviewSmall() {
    WidgetThemePreview {
        RaceSmall(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showRefresh = false
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceSmallWidth, heightDp = raceSmallHeight)
@Composable
private fun PreviewNameRefresh() {
    WidgetThemePreview {
        RaceName(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showRefresh = false
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceSmallWidth, heightDp = raceSmallHeight)
@Composable
private fun PreviewName() {
    WidgetThemePreview {
        RaceName(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showRefresh = false
        )
    }
}