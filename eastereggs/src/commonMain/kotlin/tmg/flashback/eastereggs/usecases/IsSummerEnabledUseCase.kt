package tmg.flashback.eastereggs.usecases

import org.koin.core.annotation.Single
import tmg.flashback.eastereggs.repository.EasterEggsRepository

interface IsSummerEnabledUseCase {
    operator fun invoke(): Boolean
}

@Single
internal class IsSummerEnabledUseCaseImpl(
    private val easterEggsRepository: EasterEggsRepository
): IsSummerEnabledUseCase {
    override operator fun invoke(): Boolean {
        return easterEggsRepository.isSummerEnabled
    }
}
