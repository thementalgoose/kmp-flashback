package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single

@Single
internal actual class FirebaseInstallationServiceImpl actual constructor() : FirebaseInstallationService {
    actual override suspend fun getInstallationId(): String? {
        return ""
    }
}