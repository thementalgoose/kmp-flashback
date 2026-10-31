package tmg.flashback.tracking.manager

import org.koin.core.annotation.Single
import tmg.flashback.tracking.models.TrackingState

@Single
actual class TrackingManagerImpl actual constructor(): TrackingManager {
    actual override fun getAppTrackingState(): TrackingState {
        return TrackingState.NotSupported
    }

    actual override suspend fun requestAppTracking(): Boolean {
        return true
    }
}