package tmg.flashback

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.compose.getKoin

fun MainViewController() = ComposeUIViewController {
    getKoin().get<FlashbackIOSStartup>().startup()
    App()
}