package tmg.flashback.widgets.upnext.usecases

import org.koin.core.annotation.Single

@Single(binds = [AddWidgetUseCase::class])
actual class AddWidgetUseCaseImpl actual constructor() : AddWidgetUseCase {
    actual override val isSupported: Boolean = false
    actual override operator fun invoke(): Boolean = false
}
