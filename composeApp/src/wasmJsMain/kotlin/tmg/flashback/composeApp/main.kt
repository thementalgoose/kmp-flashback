package tmg.flashback.composeApp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import tmg.flashback.composeApp.di.doInitKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    doInitKoin()

    ComposeViewport(document.body!!) {
        App()
    }
}