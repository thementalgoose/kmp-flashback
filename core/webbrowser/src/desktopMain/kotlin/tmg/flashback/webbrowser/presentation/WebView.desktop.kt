package tmg.flashback.webbrowser.presentation

import androidx.compose.runtime.Composable

@Composable
actual fun WebView(
    url: String,
    domainChanged: (String) -> Unit,
    titleChanged: (String) -> Unit,
    urlChanged: (String) -> Unit,
    progressUpdated: (Float) -> Unit
) {

}