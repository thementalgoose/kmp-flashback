package tmg.flashback.composeApp.presentation.sync

sealed interface OverallSyncState {
    object Failed: OverallSyncState
    object Successful: OverallSyncState
    data class InProgress(
        val progress: Float
    ): OverallSyncState
}

enum class SyncState {
    LOADING,
    DONE,
    FAILED
}