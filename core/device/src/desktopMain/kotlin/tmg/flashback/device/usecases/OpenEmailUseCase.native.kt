package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

@Single(binds = [OpenEmailUseCase::class])
actual class OpenEmailUseCaseImpl actual constructor(): OpenEmailUseCase {
    actual override fun invoke(
        email: String,
        title: String,
        contents: String
    ) {

    }
}