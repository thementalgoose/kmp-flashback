package tmg.flashback.feature.maintenance.presentation.forceupgrade

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import flashback.feature.maintenance.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.force_upgrade_subtitle
import flashback.presentation.localisation.generated.resources.force_upgrade_title
import flashback.presentation.localisation.generated.resources.force_upgrade_upgrade
import flashback.presentation.localisation.generated.resources.force_upgrade_validate
import flashback.presentation.localisation.generated.resources.splash_sync_info
import flashback.presentation.localisation.generated.resources.splash_title
import flashback.presentation.ui.generated.resources.ic_details_issue
import flashback.presentation.ui.generated.resources.unknown_avatar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.maintenance.presentation.forceupgrade.ForceUpgradeUiState.LOCKED
import tmg.flashback.feature.maintenance.presentation.forceupgrade.ForceUpgradeUiState.UNLOCKED
import tmg.flashback.feature.maintenance.presentation.forceupgrade.ForceUpgradeUiState.VALIDATING
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.buttons.ButtonTertiary
import tmg.flashback.style.buttons.ButtonText
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextHeadline1
import tmg.flashback.style.text.TextTitle

private val progressBarHeight: Dp = 36.dp

@Composable
fun ForceUpgradeScreen(
    unlockAndDismiss: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: ForceUpgradeViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    ForceUpgradeScreen(
        unlockAndDismiss = unlockAndDismiss,
        windowSizeClass = windowSizeClass,
        uiState = uiState.value
    )

    LaunchedEffect(uiState.value) {
        if (uiState.value == UNLOCKED) {
            unlockAndDismiss()
        }
    }
}

@Composable
fun ForceUpgradeScreen(
    unlockAndDismiss: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: ForceUpgradeUiState
) {
    Column(
        modifier = Modifier
            .background(AppTheme.colors.surface)
            .fillMaxWidth()
            .padding(AppTheme.dimens.medium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {
        Row(
            // Needed for the dialog to show the background colour in XR.
            modifier = Modifier.background(AppTheme.colors.surface),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextHeadline1(
                text = stringResource(string.force_upgrade_title)
            )
            Image(
                painter = painterResource(flashback.presentation.ui.generated.resources.Res.drawable.unknown_avatar),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
            )
        }
        TextTitle(
            text = stringResource(string.force_upgrade_subtitle),
            modifier = Modifier.padding(
                top = AppTheme.dimens.small,
                bottom = AppTheme.dimens.large
            )
        )

        ButtonPrimary(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(string.force_upgrade_upgrade),
            onClick = {

            }
        )
        ButtonText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(string.force_upgrade_validate),
            onClick = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewLocked() {
    ApplicationThemePreview {
        ForceUpgradeScreen(
            unlockAndDismiss = { },
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass,
            uiState = LOCKED
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewLoading() {
    ApplicationThemePreview {
        ForceUpgradeScreen(
            unlockAndDismiss = { },
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass,
            uiState = VALIDATING
        )
    }
}