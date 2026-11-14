package tmg.flashback.feature.notifications.presentation.global

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_notifications_runtime_description
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.ui.components.banner.MessageBanner
import tmg.flashback.ui.components.fade.Fade

@Composable
fun NotificationPrompt(
    modifier: Modifier = Modifier,
    viewModel: NotificationPromptViewModel = koinViewModel()
) {
    val promptNotifications = viewModel.promptNotification.collectAsState()
    NotificationPrompt(
        prompt = promptNotifications.value && Device.isRuntimeNotificationsSupported,
        promptRuntimeNotifications = viewModel::promptRuntimeNotifications,
        close = viewModel::close,
        modifier = modifier,
    )
}

@Composable
fun NotificationPrompt(
    prompt: Boolean,
    promptRuntimeNotifications: () -> Unit,
    close: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Fade(visible = prompt && Device.isRuntimeNotificationsSupported) {
        MessageBanner(
            modifier = modifier,
            label = string.settings_notifications_runtime_description,
            clicked = promptRuntimeNotifications,
            close = close
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewShown() {
    ApplicationThemePreview {
        NotificationPrompt(
            modifier = Modifier.padding(16.dp),
            prompt = true,
            promptRuntimeNotifications = { },
            close = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewHidden() {
    ApplicationThemePreview {
        NotificationPrompt(
            modifier = Modifier.padding(16.dp),
            prompt = false,
            promptRuntimeNotifications = { },
            close = { }
        )
    }
}