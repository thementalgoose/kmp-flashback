package tmg.flashback.webbrowser.manager

import org.koin.core.annotation.Single

interface WebManager {
    fun openWebBrowser(url: String): Boolean
}

@Single
expect class WebManagerImpl(): WebManager {
    override fun openWebBrowser(url: String): Boolean
}