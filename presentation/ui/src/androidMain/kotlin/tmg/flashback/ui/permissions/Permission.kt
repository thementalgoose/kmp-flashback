package tmg.flashback.ui.permissions

import android.Manifest

val Permission.manifestString: String
    get() = when (this) {
        Permission.Notifications -> Manifest.permission.POST_NOTIFICATIONS
    }