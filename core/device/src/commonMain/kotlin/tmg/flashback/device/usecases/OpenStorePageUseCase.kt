package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

interface OpenStorePageUseCase {
    operator fun invoke()
}

@Single(binds = [OpenStorePageUseCase::class])
expect class OpenStorePageUseCaseImpl(): OpenStorePageUseCase {
    override operator fun invoke()
}