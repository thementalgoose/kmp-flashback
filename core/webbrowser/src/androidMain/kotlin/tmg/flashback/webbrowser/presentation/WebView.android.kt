package tmg.flashback.webbrowser.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.bundleOf
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import androidx.webkit.WebViewFeature.ALGORITHMIC_DARKENING
import org.koin.compose.koinInject
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.webbrowser.repository.WebRepository

@Composable
actual fun WebView(
    url: String,
    webViewState: WebViewState
) {
    val context = LocalContext.current
    val webRepository: WebRepository = koinInject()
    val webView = remember { WebView(context) }

    LaunchedEffect(webView) {
        webViewState.goBack = {
            webView.goBack()
            webViewState.canGoBack = webView.canGoBack()
        }
    }

    val progressUpdate: (Int) -> Unit = {
        val result = it.toFloat() / 100f
        webViewState.progress = result
    }
    val webViewClient = remember {
        return@remember FlashbackWebViewClient(
            domainChanged = { webViewState.host = it },
            titleChanged = { webViewState.title = it },
            urlChanged = { webViewState.url = it },
            updateProgressToo = progressUpdate,
            updateCanGoBack = { webViewState.canGoBack = it }
        )
    }
    val webChromeClient = remember {
        return@remember FlashbackWebChromeClient(
            updateProgressToo = progressUpdate
        )
    }

    val bundle: Bundle = rememberSaveable { bundleOf() }
    AndroidView(
        factory = {
            webView.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.loadsImagesAutomatically = true
                settings.javaScriptEnabled = webRepository.enableJavascript
                scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

                this.webChromeClient = webChromeClient
                this.webViewClient = webViewClient

                if (WebViewFeature.isFeatureSupported(ALGORITHMIC_DARKENING)) {
                    WebSettingsCompat.setAlgorithmicDarkeningAllowed(this.settings, true)
                }
                this.loadUrl(url)
            }
        },
        onRelease = { webView ->
            webView.saveState(bundle)
        },
        update = { webView ->
            if (bundle.isEmpty) {
                webView.loadUrl(url)
            } else {
                webView.restoreState(bundle)
            }
        }
    )
}