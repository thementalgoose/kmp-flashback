package tmg.flashback.widgets.upnext.usecases

import org.koin.core.annotation.Single

@Single(binds = [RefreshWidgetsUseCase::class])
actual class RefreshWidgetsUseCaseImpl actual constructor() : RefreshWidgetsUseCase {
    actual override operator fun invoke() { }
}
