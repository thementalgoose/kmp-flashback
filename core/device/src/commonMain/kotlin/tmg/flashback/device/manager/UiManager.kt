package tmg.flashback.device.manager

import org.jetbrains.compose.resources.StringResource

interface UiManager {
    fun showToUser(resource: StringResource)
    fun showToUser(message: String)
}