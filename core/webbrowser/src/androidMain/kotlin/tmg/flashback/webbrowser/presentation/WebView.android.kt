package tmg.flashback.webbrowser.presentation

import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import org.koin.compose.koinInject
import tmg.flashback.webbrowser.repository.WebRepository

@Composable
actual fun WebView(
    url: String,
    domainChanged: (String) -> Unit,
    titleChanged: (String) -> Unit,
    urlChanged: (String) -> Unit,
    progressUpdated: (Float) -> Unit
) {
    val webRepository: WebRepository = koinInject()

    val webViewClient = remember {
        return@remember FlashbackWebViewClient(
            domainChanged = { domainChanged(it) },
            titleChanged = { titleChanged(it) },
            urlChanged = { urlChanged(it) }
        )
    }
    val webChromeClient = remember {
        return@remember FlashbackWebChromeClient(
            updateProgressToo = {
                val result = it.toFloat() / 100f
                progressUpdated(result)
            }
        )
    }

    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.loadsImagesAutomatically = true
                settings.javaScriptEnabled = webRepository.enableJavascript
                scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

                this.webChromeClient = webChromeClient
                this.webViewClient = webViewClient
            }
        },
        update = {
            it.loadUrl(url)
        }
    )
}