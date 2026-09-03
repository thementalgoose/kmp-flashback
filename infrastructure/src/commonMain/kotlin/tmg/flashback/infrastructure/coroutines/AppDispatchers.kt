package tmg.flashback.infrastructure.coroutines

import kotlinx.coroutines.CoroutineDispatcher

expect val CoroutineDispatcher.appIO: CoroutineDispatcher