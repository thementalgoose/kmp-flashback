package tmg.flashback.widgets.upnext.usecases

import org.koin.core.annotation.Single

@Single
internal class HasUpNextWidgetsUseCaseImpl(): HasUpNextWidgetsUseCase {
    override fun invoke(): Boolean = false
}