package tmg.flashback.composeApp.firebase

internal expect class FirebaseInstallationServiceImpl(): FirebaseInstallationService {
    override suspend fun getInstallationId(): String?
}