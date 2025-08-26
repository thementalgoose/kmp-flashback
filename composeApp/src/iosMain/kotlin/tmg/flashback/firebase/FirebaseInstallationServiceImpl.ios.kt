package tmg.flashback.firebase

import cocoapods.FirebaseInstallations.FIRInstallations
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
internal actual class FirebaseInstallationServiceImpl actual constructor() : FirebaseInstallationService {

    private val instance: FIRInstallations
        get() = FIRInstallations.installations()

    actual override suspend fun getInstallationId(): String? = suspendCoroutine { continuation ->
        instance.installationIDWithCompletion { id, error ->
            continuation.resumeWith(Result.success(id))
        }
    }
}