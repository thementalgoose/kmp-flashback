package tmg.flashback.feature.search.usecases

interface IsSearchEnabledUseCase {
    operator fun invoke(): Boolean
}

internal class IsSearchEnabledUseCseImpl(): IsSearchEnabledUseCase {
    override operator fun invoke(): Boolean {
        return false
    }
}