package tmg.flashback.widgets.upnext.presentation.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.widgets.upnext.presentation.components.CountryIcon
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.presentation.preview.fakeSprintWeekend
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody1

internal const val raceIconWidth = 42
internal const val raceIconHeight = 42
internal val raceIconConfiguration = DpSize(raceIconWidth.dp, raceIconHeight.dp)

@Composable
internal fun RaceIcon(
    overviewRace: OverviewRace,
    modifier: GlanceModifier = GlanceModifier,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = androidx.glance.layout.Alignment.CenterVertically,
        horizontalAlignment = androidx.glance.layout.Alignment.CenterHorizontally
    ) {
        CountryIcon(
            context = context,
            country = overviewRace.country,
            countryISO = overviewRace.countryISO,
        )
        TextBody1(
            text = "#${overviewRace.round}",
            weight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceIconWidth, heightDp = raceIconHeight)
@Composable
private fun Preview() {
    WidgetThemePreview {
        RaceIcon(
            overviewRace = fakeOverviewRace,
        )
    }
}