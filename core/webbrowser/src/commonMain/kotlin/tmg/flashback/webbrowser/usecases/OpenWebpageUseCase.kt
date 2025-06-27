package tmg.flashback.webbrowser.usecases

import tmg.flashback.webbrowser.manager.WebManager

interface OpenWebpageUseCase {
    operator fun invoke(
        url: String,
        title: String,
        forceExternal: Boolean = false
    ): Boolean
}

class OpenWebpageUseCaseImpl(
    private val isInAppBrowserEnabledUseCase: IsInAppBrowserEnabledUseCase,
    private val webManager: WebManager
): OpenWebpageUseCase {
    override operator fun invoke(
        url: String,
        title: String,
        forceExternal: Boolean
    ): Boolean {
        if (!isInAppBrowserEnabledUseCase()) {
            webManager.openWebBrowser(url)
            return true
        }

        if (forceExternal) {
            //
        }

        return false
    }
}