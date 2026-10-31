package tmg.flashback.widgets.upnext.usecases

import org.koin.core.annotation.Single

@Single(binds = [RefreshWidgetsUseCase::class])
expect class RefreshWidgetsUseCaseImpl(): RefreshWidgetsUseCase {
    override fun invoke()
}