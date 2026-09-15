package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

interface OpenWebpageUseCase {
    operator fun invoke(url: String, title: String = url)
}

@Single(binds = [OpenWebpageUseCase::class])
expect class OpenWebpageUseCaseImpl(): OpenWebpageUseCase {
    override fun invoke(url: String, title: String)
}