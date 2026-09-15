package tmg.flashback.composeApp.usecases

import org.koin.core.annotation.Single
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.composeApp.firebase.FirebaseInstallationService
import tmg.flashback.infrastructure.log.logDebug

@Single(binds = [StoreFirebaseInstallationIdUseCase::class])
class StoreFirebaseInstallationIdUseCaseImpl(
    private val deviceRepository: DeviceRepository,
    private val firebaseInstallationService: FirebaseInstallationService
): StoreFirebaseInstallationIdUseCase {
    override suspend operator fun invoke(): Boolean {
        val id = firebaseInstallationService.getInstallationId()
        logDebug("Installations", "Saving installation id '$id'")
        if (id != null) {
            deviceRepository.installationId = id
        }
        return id != null
    }
}