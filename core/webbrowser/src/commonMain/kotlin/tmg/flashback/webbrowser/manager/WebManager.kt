package tmg.flashback.webbrowser.manager

interface WebManager {
    fun openWebBrowser(url: String): Boolean
}

expect class WebManagerImpl(): WebManager {
    override fun openWebBrowser(url: String): Boolean
}