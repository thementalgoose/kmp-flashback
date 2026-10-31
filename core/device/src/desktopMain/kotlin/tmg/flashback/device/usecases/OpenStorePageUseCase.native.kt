package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

@Single(binds = [OpenStorePageUseCase::class])
actual class OpenStorePageUseCaseImpl actual constructor(): OpenStorePageUseCase {
    actual override fun invoke() {

    }
}