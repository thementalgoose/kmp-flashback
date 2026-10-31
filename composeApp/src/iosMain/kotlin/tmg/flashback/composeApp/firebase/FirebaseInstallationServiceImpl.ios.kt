package tmg.flashback.composeApp.firebase

import cocoapods.FirebaseInstallations.FIRInstallations
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.annotation.Single
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
@Single
internal actual class FirebaseInstallationServiceImpl actual constructor() : FirebaseInstallationService {

    private val instance: FIRInstallations
        get() = FIRInstallations.installations()

    actual override suspend fun getInstallationId(): String? = suspendCoroutine { continuation ->
        instance.installationIDWithCompletion { id, error ->
            continuation.resumeWith(Result.success(id))
        }
    }
}