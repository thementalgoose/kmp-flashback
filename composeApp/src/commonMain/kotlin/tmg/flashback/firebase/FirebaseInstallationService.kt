package tmg.flashback.firebase

interface FirebaseInstallationService {
    suspend fun getInstallationId(): String?
}