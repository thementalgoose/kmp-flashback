package tmg.flashback.feature.season.presentation.calendar

import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CalendarScreen(
    viewModel: CalendarScreenViewModel = koinViewModel(),
    windowSizeClass: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {

}
