package tmg.flashback.feature.notifications.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_notifications_runtime_description
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.ui.components.banner.MessageBanner
import tmg.flashback.ui.components.fade.Fade

@Composable
fun NotificationPrompt(
    modifier: Modifier = Modifier,
    viewModel: NotificationPromptViewModel = koinViewModel()
) {
    val promptNotifications = viewModel.promptNotification.collectAsState()
    Fade(visible = promptNotifications.value && Device.isRuntimeNotificationsSupported) {
        MessageBanner(
            modifier = modifier,
            label = string.settings_notifications_runtime_description,
            clicked = viewModel::promptRuntimeNotifications,
            close = viewModel::close
        )
    }
}