package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

@Single(binds = [ShareWebpageUseCase::class])
actual class ShareWebpageUseCaseImpl actual constructor() :
    ShareWebpageUseCase {
    actual override operator fun invoke(url: String, title: String) {
    }
}