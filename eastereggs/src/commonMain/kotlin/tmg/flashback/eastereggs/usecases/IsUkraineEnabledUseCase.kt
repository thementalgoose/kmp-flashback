package tmg.flashback.eastereggs.usecases

import tmg.flashback.eastereggs.repository.EasterEggsRepository

interface IsUkraineEnabledUseCase {
    operator fun invoke(): Boolean
}

internal class IsUkraineEnabledUseCaseImpl(
    private val easterEggsRepository: EasterEggsRepository
): IsUkraineEnabledUseCase {
    override operator fun invoke(): Boolean {
        return easterEggsRepository.isUkraineEnabled
    }
}