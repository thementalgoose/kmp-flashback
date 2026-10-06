package tmg.flashback.widgets.upnext.usecases

interface AddWidgetUseCase {
    val isSupported: Boolean
    operator fun invoke(): Boolean
}
