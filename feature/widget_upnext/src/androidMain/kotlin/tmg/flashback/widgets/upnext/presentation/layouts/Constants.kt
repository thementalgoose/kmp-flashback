package tmg.flashback.widgets.upnext.presentation.layouts

internal fun String.format() = when (this.lowercase()) {
    "sprint qualifying" -> "Sprint Quali"
    "sprint shootout" -> "Sprint Q.."
    "qualifying" -> "Quali.."
    "qualify" -> "Quali.."
    else -> this
}