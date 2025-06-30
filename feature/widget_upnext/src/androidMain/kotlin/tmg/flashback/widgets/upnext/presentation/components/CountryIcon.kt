package tmg.flashback.widgets.upnext.presentation.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.layout.size
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.utils.DrawableUtils.getFlagResourceAlpha3

@Composable
internal fun CountryIcon(
    context: Context,
    country: String,
    countryISO: String,
    modifier: GlanceModifier = GlanceModifier.size(24.dp, 24.dp)
) {
    val resource = context.getFlagResourceAlpha3(countryISO)
    Image(
        provider = ImageProvider(resId = resource),
        contentDescription = country,
        modifier = modifier
    )
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun Preview() {
    WidgetThemePreview {
        CountryIcon(
            context = LocalContext.current,
            country = "Italy",
            countryISO = "ITA",
        )
    }
}