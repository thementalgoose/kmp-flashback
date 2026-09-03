package tmg.flashback.data.repo.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val dispatcher: CoroutineDispatcher = Dispatchers.IO