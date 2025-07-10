package tmg.flashback.device.usecases

interface OpenLocationUseCase {
    operator fun invoke(lat: Double, lng: Double, name: String? = null)
}

expect class OpenLocationUseCaseImpl(): OpenLocationUseCase {
    override fun invoke(lat: Double, lng: Double, name: String?)
}