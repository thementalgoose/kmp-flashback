package tmg.flashback.device.usecases

interface OpenEmailUseCase {
    operator fun invoke(email: String)
}

expect class OpenEmailUseCaseImpl(): OpenEmailUseCase {
    override fun invoke(email: String)
}