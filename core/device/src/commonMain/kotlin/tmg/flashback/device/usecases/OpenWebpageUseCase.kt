package tmg.flashback.device.usecases

interface OpenWebpageUseCase {
    operator fun invoke(url: String)
}

expect class OpenWebpageUseCaseImpl(): OpenWebpageUseCase {
    override fun invoke(url: String)
}