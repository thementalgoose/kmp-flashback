package tmg.flashback

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.compose.getKoin
import tmg.flashback.di.doInitKoin

fun main() = application {

    // Initialise Koin
    doInitKoin()
    getKoin().get<FlashbackDesktopStartup>().start()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Flashback",
    ) {
        App()
    }
}