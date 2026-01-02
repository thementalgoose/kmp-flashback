package tmg.flashback.composeApp.firebase

interface FirebaseInstallationService {
    suspend fun getInstallationId(): String?
}