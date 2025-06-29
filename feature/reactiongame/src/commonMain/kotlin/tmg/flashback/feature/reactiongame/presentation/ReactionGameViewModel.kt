package tmg.flashback.feature.reactiongame.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tmg.flashback.feature.reactiongame.manager.LightsOutDelayProvider
import tmg.flashback.infrastructure.datetime.TimeManager

class ReactionGameViewModel(
    private val lightsOutDelayProvider: LightsOutDelayProvider,
    private val timeManager: TimeManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private companion object {
        const val TIME_BETWEEN_LIGHTS = 1000L
        const val LIGHTS = 5
        const val TEST_TIMEOUT = 2500L
    }

    private var job = SupervisorJob() + ioDispatcher

    private var lightsOutTime: Long = 0

    private val _uiState: MutableStateFlow<ReactionUiState> = MutableStateFlow(ReactionUiState.Start)
    val uiState: StateFlow<ReactionUiState> = _uiState

    fun reset() {
        _uiState.value = ReactionUiState.Start
    }

    fun start() {

        lightsOutTime = 0L
        val lightsOutDelay = lightsOutDelayProvider.getDelay()
        job = viewModelScope.launch(ioDispatcher) {
            // Count lights in
            for (i in 0..LIGHTS) {
                _uiState.value = ReactionUiState.Game(
                    lights = i
                )
                delay(TIME_BETWEEN_LIGHTS)
            }

            // Hold delay
            delay(lightsOutDelay)
            setLightsOutTime()
            _uiState.value = ReactionUiState.Game(
                lights = 0,
                hasDisplayedSequence = true
            )

            ensureActive()
            delay(TEST_TIMEOUT)

            ensureActive()
            _uiState.value = ReactionUiState.Missed
        }
    }

    fun setLightsOutTime() {
        this.lightsOutTime = timeManager.nowMillis
    }

    fun react() {
        val clickedTime = timeManager.nowMillis
        val reactionTime = clickedTime - lightsOutTime
        job.cancel()

        // Clicked before lights out. Jump start
        if (this.lightsOutTime == 0L || reactionTime < 0L) {
            _uiState.value = ReactionUiState.JumpStart
            return
        }

        _uiState.value = ReactionUiState.Results(
            timeMillis = reactionTime,
            tier = ReactionResultTier.toTier(reactionTime),
            percentage = (reactionTime / 500f).coerceIn(0f, 1f)
        )
        this.lightsOutTime = 0L
    }
}