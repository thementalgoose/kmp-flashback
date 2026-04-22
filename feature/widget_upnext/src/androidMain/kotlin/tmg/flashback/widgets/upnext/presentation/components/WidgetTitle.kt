package tmg.flashback.widgets.upnext.presentation.components

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.marginSmall
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody1
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody2
import tmg.flashback.widgets.upnext.presentation.style.text.TextTitle

@Composable
internal fun WidgetTitle(
    overviewRace: OverviewRace,
    modifier: GlanceModifier = GlanceModifier
) {
    val context = LocalContext.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountryIcon(
            context = context,
            country = overviewRace.country,
            countryISO = overviewRace.countryISO,
        )
        Column(
            modifier = GlanceModifier.defaultWeight()
        ) {
            TextTitle(
                weight = FontWeight.Bold,
                modifier = GlanceModifier.padding(start = marginSmall),
                text = overviewRace.raceName
            )
            TextBody2(
                modifier = GlanceModifier.padding(start = marginSmall),
                text = overviewRace.circuitName
            )
        }
        TextBody1(
            text = "#${overviewRace.round}",
            weight = FontWeight.Bold
        )
    }
}

@ExperimentalGlancePreviewApi
@Preview
@Composable
private fun Preview() {
    WidgetThemePreview {
        WidgetTitle(
            overviewRace = fakeOverviewRace
        )
    }
}