package tmg.flashback.widgets.upnext.usecases

import org.koin.core.annotation.Single

@Single(binds = [AddWidgetUseCase::class])
expect class AddWidgetUseCaseImpl(): AddWidgetUseCase {
    override val isSupported: Boolean
    override operator fun invoke(): Boolean
}
