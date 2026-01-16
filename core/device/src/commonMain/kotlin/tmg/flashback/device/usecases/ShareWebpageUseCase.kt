package tmg.flashback.device.usecases

interface ShareWebpageUseCase {
    operator fun invoke(url: String, title: String = url)
}

expect class ShareWebpageUseCaseImpl(): ShareWebpageUseCase {
    override fun invoke(url: String, title: String)
}