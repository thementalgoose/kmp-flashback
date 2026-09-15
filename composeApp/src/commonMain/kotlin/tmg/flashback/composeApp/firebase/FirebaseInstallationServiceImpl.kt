package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single
import tmg.flashback.composeApp.firebase.FirebaseInstallationService

@Single(binds = [FirebaseInstallationService::class])
internal expect class FirebaseInstallationServiceImpl(): FirebaseInstallationService {
    override suspend fun getInstallationId(): String?
}