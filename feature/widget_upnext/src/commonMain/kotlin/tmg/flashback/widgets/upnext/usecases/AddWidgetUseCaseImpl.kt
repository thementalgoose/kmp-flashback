package tmg.flashback.widgets.upnext.usecases

expect class AddWidgetUseCaseImpl(): AddWidgetUseCase {
    override val isSupported: Boolean
    override operator fun invoke(): Boolean
}
