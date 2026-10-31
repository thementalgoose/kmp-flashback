package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

@Single(binds = [OpenLocationUseCase::class])
actual class OpenLocationUseCaseImpl actual constructor(): OpenLocationUseCase {
    actual override fun invoke(lat: Double, lng: Double, name: String?) {

    }
}