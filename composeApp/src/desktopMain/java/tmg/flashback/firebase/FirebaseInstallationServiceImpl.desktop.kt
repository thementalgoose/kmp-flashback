package tmg.flashback.firebase

internal actual class FirebaseInstallationServiceImpl actual constructor() : FirebaseInstallationService {
    actual override suspend fun getInstallationId(): String? {
        return ""
    }
}