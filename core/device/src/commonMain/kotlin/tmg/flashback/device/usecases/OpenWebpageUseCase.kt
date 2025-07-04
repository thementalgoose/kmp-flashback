package tmg.flashback.device.usecases

interface OpenWebpageUseCase {
    operator fun invoke(url: String, title: String = url)
}

expect class OpenWebpageUseCaseImpl(): OpenWebpageUseCase {
    override fun invoke(url: String, title: String)
}