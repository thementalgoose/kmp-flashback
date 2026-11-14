package tmg.flashback.feature.notifications.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_notifications_runtime_available
import flashback.presentation.localisation.generated.resources.settings_notifications_runtime_description
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.ui.components.banner.MessageBanner
import tmg.flashback.ui.components.fade.Fade

@Composable
fun NotificationResult(
    result: NotificationResultsAvailable,
    modifier: Modifier = Modifier,
    viewModel: NotificationResultViewModel = koinViewModel()
) {
    LaunchedEffect(result) {
        viewModel.forResult(result)
    }
    val promptNotifications = viewModel.promptNotification.collectAsState()
    Fade(visible = promptNotifications.value && Device.isRuntimeNotificationsSupported) {
        val label = stringResource(result.channelLabel).lowercase()
        MessageBanner(
            modifier = modifier,
            text = stringResource(string.settings_notifications_runtime_available, label),
            clicked = {
                viewModel.promptRuntimeNotifications(result)
            },
            close = {
                viewModel.close(result)
            }
        )
    }
}