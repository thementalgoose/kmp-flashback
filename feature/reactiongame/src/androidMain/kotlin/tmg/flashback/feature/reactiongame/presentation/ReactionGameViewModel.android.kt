package tmg.flashback.feature.reactiongame.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val dispatcher: CoroutineDispatcher = Dispatchers.IO