package tmg.flashback.eastereggs.usecases

import tmg.flashback.eastereggs.repository.EasterEggsRepository

interface IsSummerEnabledUseCase {
    operator fun invoke(): Boolean
}

internal class IsSummerEnabledUseCaseImpl(
    private val easterEggsRepository: EasterEggsRepository
): IsSummerEnabledUseCase {
    override operator fun invoke(): Boolean {
        return easterEggsRepository.isSummerEnabled
    }
}
