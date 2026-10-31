package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single

@Single
internal expect class FirebaseInstallationServiceImpl(): FirebaseInstallationService {
    override suspend fun getInstallationId(): String?
}