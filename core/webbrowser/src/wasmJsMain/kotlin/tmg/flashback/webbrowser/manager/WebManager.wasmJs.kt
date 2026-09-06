package tmg.flashback.webbrowser.manager

actual class WebManagerImpl actual constructor() : WebManager {
    actual override fun openWebBrowser(url: String): Boolean = false
}
