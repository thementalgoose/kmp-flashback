package tmg.flashback.tracking.manager

import platform.AppTrackingTransparency.ATTrackingManager
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusAuthorized
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusDenied
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusNotDetermined
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusRestricted
import tmg.flashback.tracking.models.TrackingState
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class TrackingManagerImpl actual constructor(): TrackingManager {

    private fun initialise() {
        ATTrackingManager.initialize()
    }

    actual override fun getAppTrackingState(): TrackingState {
        initialise()
        val state = ATTrackingManager.trackingAuthorizationStatus
        return when (state) {
            ATTrackingManagerAuthorizationStatusNotDetermined -> TrackingState.Unknown
            ATTrackingManagerAuthorizationStatusDenied -> TrackingState.Denied
            ATTrackingManagerAuthorizationStatusRestricted -> TrackingState.Granted
            ATTrackingManagerAuthorizationStatusAuthorized -> TrackingState.Granted
            else -> TrackingState.NotSupported
        }
    }

    actual override suspend fun requestAppTracking(): Boolean = suspendCoroutine { continuation ->
        initialise()
        ATTrackingManager.requestTrackingAuthorizationWithCompletionHandler { status ->
            continuation.resume(true)
        }
    }
}