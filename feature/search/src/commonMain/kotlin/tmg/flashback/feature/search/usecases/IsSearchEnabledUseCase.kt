package tmg.flashback.feature.search.usecases

import org.koin.core.annotation.Single

interface IsSearchEnabledUseCase {
    operator fun invoke(): Boolean
}

@Single
internal class IsSearchEnabledUseCseImpl(): IsSearchEnabledUseCase {
    override operator fun invoke(): Boolean {
        return false
    }
}