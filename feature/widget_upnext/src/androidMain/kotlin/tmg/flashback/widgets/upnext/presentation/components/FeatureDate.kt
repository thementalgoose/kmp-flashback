package tmg.flashback.widgets.upnext.presentation.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.text.TextFeature
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.utils.labels
import tmg.flashback.widgets.upnext.utils.raceSchedule

@Composable
fun FeatureDate(
    featureTextSize: TextUnit = 32.sp,
    context: Context,
    overviewRace: OverviewRace,
    modifier: GlanceModifier = GlanceModifier
) {
    val raceSchedule = overviewRace.raceSchedule()
    if (raceSchedule != null) {
        val (day, time) = raceSchedule.labels()
        Column(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFeature(
                fontSize = featureTextSize,
                text = "$day ($time)",
                color = GlanceTheme.colors.onBackground.getColor(context),
            )
        }
    } else {
        val (day, time) = overviewRace.labels()
        Column(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFeature(
                fontSize = featureTextSize,
                text = "$day ($time)",
                color = GlanceTheme.colors.onBackground.getColor(context),
            )
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun Preview() {
    WidgetThemePreview {
        FeatureDate(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace
        )
    }
}