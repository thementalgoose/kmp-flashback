package tmg.flashback.tracking.manager

import tmg.flashback.tracking.models.TrackingState

actual class TrackingManagerImpl actual constructor(): TrackingManager {
    actual override fun getAppTrackingState(): TrackingState {
        return TrackingState.NotSupported
    }

    actual override suspend fun requestAppTracking(): Boolean {
        return true
    }
}