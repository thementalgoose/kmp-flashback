package tmg.flashback.feature.season.presentation.shared.device_time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.feature_banner_device_time
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.ui.components.banner.MessageBanner
import tmg.flashback.ui.components.fade.Fade

@Composable
internal fun DeviceTimePrompt(
    viewModel: DeviceTimeViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val showBanner = viewModel.uiState.collectAsState()
    Fade(visible = showBanner.value.show) {
        MessageBanner(
            modifier = modifier,
            text = stringResource(string.feature_banner_device_time, showBanner.value.timezone),
            clicked = viewModel::acknowledge,
            close = viewModel::acknowledge
        )
    }
}