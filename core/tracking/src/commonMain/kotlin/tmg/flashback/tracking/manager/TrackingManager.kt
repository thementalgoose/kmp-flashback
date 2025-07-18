package tmg.flashback.tracking.manager

import tmg.flashback.tracking.models.TrackingState

interface TrackingManager {
    fun getAppTrackingState(): TrackingState
    suspend fun requestAppTracking(): Boolean
}

expect class TrackingManagerImpl(): TrackingManager {
    override fun getAppTrackingState(): TrackingState
    override suspend fun requestAppTracking(): Boolean
}