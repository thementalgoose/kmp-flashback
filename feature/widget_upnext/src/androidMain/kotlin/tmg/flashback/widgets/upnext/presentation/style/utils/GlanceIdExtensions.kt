package tmg.flashback.widgets.upnext.presentation.style.utils

import androidx.annotation.Discouraged
import androidx.glance.GlanceId

@get:Discouraged(message = "Before using this again, try and find a better approach!")
val GlanceId.appWidgetId: Int
    get() = this.toString().filter { it.isDigit() }.toInt()