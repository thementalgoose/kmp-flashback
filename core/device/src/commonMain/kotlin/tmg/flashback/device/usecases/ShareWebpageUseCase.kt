package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

interface ShareWebpageUseCase {
    operator fun invoke(url: String, title: String = url)
}

@Single(binds = [ShareWebpageUseCase::class])
expect class ShareWebpageUseCaseImpl(): ShareWebpageUseCase {
    override fun invoke(url: String, title: String)
}