package tmg.flashback.tracking.manager

import org.koin.core.annotation.Single
import tmg.flashback.tracking.models.TrackingState

interface TrackingManager {
    fun getAppTrackingState(): TrackingState
    suspend fun requestAppTracking(): Boolean
}

@Single
expect class TrackingManagerImpl(): TrackingManager {
    override fun getAppTrackingState(): TrackingState
    override suspend fun requestAppTracking(): Boolean
}