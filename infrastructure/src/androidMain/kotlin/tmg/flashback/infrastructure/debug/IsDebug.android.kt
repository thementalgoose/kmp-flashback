package tmg.flashback.infrastructure.debug

import tmg.flashback.infrastructure.BuildConfig

actual fun isDebug(): Boolean = BuildConfig.DEBUG