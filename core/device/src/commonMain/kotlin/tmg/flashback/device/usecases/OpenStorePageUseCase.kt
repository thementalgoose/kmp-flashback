package tmg.flashback.device.usecases

interface OpenStorePageUseCase {
    operator fun invoke()
}

expect class OpenStorePageUseCaseImpl(): OpenStorePageUseCase {
    override operator fun invoke()
}