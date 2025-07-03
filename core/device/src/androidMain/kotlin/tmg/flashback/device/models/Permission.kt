package tmg.flashback.device.models

import android.Manifest

val Permission.manifestKey: String
    get() = when (this) {
        Permission.Notifications -> Manifest.permission.POST_NOTIFICATIONS
    }