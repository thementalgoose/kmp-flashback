package tmg.flashback.firebase

internal expect class FirebaseInstallationServiceImpl(): FirebaseInstallationService {
    override suspend fun getInstallationId(): String?
}