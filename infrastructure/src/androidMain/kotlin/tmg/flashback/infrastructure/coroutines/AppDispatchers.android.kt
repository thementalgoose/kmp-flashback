package tmg.flashback.infrastructure.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val CoroutineDispatcher.appIO: CoroutineDispatcher
    get() = Dispatchers.IO