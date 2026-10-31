package tmg.flashback.composeApp.firebase

import com.google.firebase.installations.FirebaseInstallations
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single
import tmg.flashback.infrastructure.log.logException

@Single
internal actual class FirebaseInstallationServiceImpl actual constructor() : FirebaseInstallationService {

    private val instance: FirebaseInstallations
        get() = FirebaseInstallations.getInstance()

    actual override suspend fun getInstallationId(): String? {
        try {
            return instance.id.await()
        } catch (e: Throwable) {
            logException(e)
            return null
        }
    }
}