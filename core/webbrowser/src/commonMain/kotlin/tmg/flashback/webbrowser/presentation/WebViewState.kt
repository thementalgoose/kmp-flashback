package tmg.flashback.webbrowser.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class WebViewState internal constructor(
    url: String,
    title: String,
) {
    var host by mutableStateOf(url)
    var title by mutableStateOf(title)
    var url by mutableStateOf(url)
    var progress by mutableStateOf(0f)

    var canGoBack by mutableStateOf(false)
    var goBack by mutableStateOf({})

    fun clickBack() { goBack() }
}

@Composable
fun rememberWebViewState(
    url: String = "",
    title: String = "",
): WebViewState {
    return remember {
        WebViewState(
            url = url,
            title = title,
        )
    }
}