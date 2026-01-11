package tmg.flashback.webbrowser.presentation

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri

internal class FlashbackWebChromeClient(
    val updateProgressToo: (progress: Int) -> Unit
): WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        updateProgressToo(newProgress)
        super.onProgressChanged(view, newProgress)
    }
}

internal class FlashbackWebViewClient(
    val domainChanged: (domain: String) -> Unit,
    val titleChanged: (title: String) -> Unit,
    val urlChanged: (url: String) -> Unit,
    val updateProgressToo: (progress: Int) -> Unit,
    val updateCanGoBack: (canGoBack: Boolean) -> Unit,
): WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        updateCanGoBack(view?.canGoBack() == true)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString() ?: return false
        view?.loadUrl(url)
        val uri = url.toUri()
        urlChanged(url)
        domainChanged(uri.host ?: "")
        updateCanGoBack(view?.canGoBack() == true)
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        titleChanged(view?.title ?: "...")
        updateProgressToo(100)
        updateCanGoBack(view?.canGoBack() == true)
    }
}