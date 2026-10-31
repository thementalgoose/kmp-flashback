package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

interface OpenEmailUseCase {
    operator fun invoke(
        email: String,
        title: String = "",
        contents: String = ""
    )
}

@Single(binds = [OpenEmailUseCase::class])
expect class OpenEmailUseCaseImpl() : OpenEmailUseCase {
    override fun invoke(
        email: String,
        title: String,
        contents: String
    )
}