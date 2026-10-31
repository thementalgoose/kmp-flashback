package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

interface OpenLocationUseCase {
    operator fun invoke(lat: Double, lng: Double, name: String? = null)
}

@Single(binds = [OpenLocationUseCase::class])
expect class OpenLocationUseCaseImpl(): OpenLocationUseCase {
    override fun invoke(lat: Double, lng: Double, name: String?)
}