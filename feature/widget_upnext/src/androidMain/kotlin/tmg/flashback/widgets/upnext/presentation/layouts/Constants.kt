package tmg.flashback.widgets.upnext.presentation.layouts

internal fun String.shortenLabel() = when (this.lowercase()) {
    "sprint qualifying" -> "Sprint Q.."
    "sprint shootout" -> "Sprint Q.."
    "qualifying" -> "Quali.."
    "qualify" -> "Quali.."
    else -> this
}