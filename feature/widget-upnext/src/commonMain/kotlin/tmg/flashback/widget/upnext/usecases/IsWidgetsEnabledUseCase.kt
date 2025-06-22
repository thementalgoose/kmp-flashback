package tmg.flashback.widget.upnext.usecases

interface IsWidgetsEnabledUseCase {
    operator fun invoke(): Boolean
}

internal class IsWidgetsEnabledUseCaseImpl(): IsWidgetsEnabledUseCase {
    override fun invoke(): Boolean {
        return false
    }
}