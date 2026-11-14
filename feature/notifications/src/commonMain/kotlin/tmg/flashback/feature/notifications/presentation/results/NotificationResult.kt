package tmg.flashback.feature.notifications.presentation.results

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_notifications_runtime_available
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.presentation.global.NotificationPrompt
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
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
    NotificationResult(
        prompt = promptNotifications.value && Device.isRuntimeNotificationsSupported,
        result = result,
        promptRuntimeNotifications = {
            viewModel.promptRuntimeNotifications(result)
        },
        close = {
            viewModel.close(result)
        },
        modifier = modifier,
    )
}

@Composable
private fun NotificationResult(
    result: NotificationResultsAvailable,
    prompt: Boolean,
    promptRuntimeNotifications: () -> Unit,
    close: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Fade(visible = prompt) {
        val label = stringResource(result.channelLabel).lowercase()
        MessageBanner(
            modifier = modifier,
            text = stringResource(string.settings_notifications_runtime_available, label),
            clicked = promptRuntimeNotifications,
            close = close
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewShown() {
    ApplicationThemePreview {
        NotificationResult(
            modifier = Modifier.padding(16.dp),
            result = NotificationResultsAvailable.RACE,
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
        NotificationResult(
            modifier = Modifier.padding(16.dp),
            result = NotificationResultsAvailable.RACE,
            prompt = false,
            promptRuntimeNotifications = { },
            close = { }
        )
    }
}