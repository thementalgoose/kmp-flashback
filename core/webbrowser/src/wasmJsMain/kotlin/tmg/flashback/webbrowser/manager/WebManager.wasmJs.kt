package tmg.flashback.webbrowser.manager

import org.koin.core.annotation.Single

@Single
actual class WebManagerImpl actual constructor() : WebManager {
    actual override fun openWebBrowser(url: String): Boolean = false
}
