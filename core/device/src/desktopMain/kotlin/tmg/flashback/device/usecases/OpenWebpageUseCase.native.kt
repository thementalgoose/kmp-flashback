package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

@Single(binds = [OpenWebpageUseCase::class])
actual class OpenWebpageUseCaseImpl actual constructor(): OpenWebpageUseCase {
    actual override fun invoke(url: String, title: String) {

    }
}