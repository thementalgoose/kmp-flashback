package tmg.flashback.feature.maintenance.presentation.forceupgrade

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import flashback.feature.maintenance.generated.resources.Res
import flashback.feature.maintenance.generated.resources.force_upgrade_apple
import flashback.feature.maintenance.generated.resources.force_upgrade_github
import flashback.feature.maintenance.generated.resources.force_upgrade_play
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.button_apple_app
import flashback.presentation.localisation.generated.resources.button_github
import flashback.presentation.localisation.generated.resources.button_play
import flashback.presentation.localisation.generated.resources.force_upgrade_subtitle
import flashback.presentation.localisation.generated.resources.force_upgrade_title
import flashback.presentation.localisation.generated.resources.force_upgrade_upgrade
import flashback.presentation.localisation.generated.resources.force_upgrade_validate
import flashback.presentation.ui.generated.resources.unknown_avatar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.maintenance.presentation.forceupgrade.ForceUpgradeUiState.LOCKED
import tmg.flashback.feature.maintenance.presentation.forceupgrade.ForceUpgradeUiState.UNLOCKED
import tmg.flashback.feature.maintenance.presentation.forceupgrade.ForceUpgradeUiState.VALIDATING
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.buttons.ButtonText
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextHeadline1
import tmg.flashback.style.text.TextTitle

@Composable
fun ForceUpgradeScreen(
    unlockAndDismiss: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: ForceUpgradeViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    ForceUpgradeScreen(
        refreshConfig = viewModel::validate,
        openStore = viewModel::goToStore,
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
    openStore: () -> Unit,
    refreshConfig: () -> Unit,
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

        val icon = when (Device.platform) {
            Platform.Android -> Res.drawable.force_upgrade_play
            Platform.IOS -> Res.drawable.force_upgrade_apple
            Platform.Desktop -> Res.drawable.force_upgrade_github
            Platform.WasmJs -> Res.drawable.force_upgrade_github
        }
        val label = when (Device.platform) {
            Platform.Android -> stringResource(string.button_play)
            Platform.IOS -> stringResource(string.button_apple_app)
            Platform.Desktop -> stringResource(string.button_github)
            Platform.WasmJs -> stringResource(string.button_github)
        }

        Image(
            painter = painterResource(icon),
            contentDescription = label,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(AppTheme.dimens.large))

        ButtonPrimary(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(string.force_upgrade_upgrade),
            onClick = openStore,
            enabled = uiState == LOCKED
        )

        ButtonText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(string.force_upgrade_validate),
            onClick = refreshConfig,
            enabled = uiState != VALIDATING
        )

        AnimatedContent(
            targetState = uiState,
            content = {
                when (it) {
                    VALIDATING -> LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                    )
                    LOCKED,
                    UNLOCKED -> Box(Modifier.height(12.dp))
                }
            }
        )

        Spacer(modifier = Modifier
            .height(
                WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + AppTheme.dimens.medlarge
            )
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewLocked() {
    ApplicationThemePreview {
        ForceUpgradeScreen(
            openStore = { },
            refreshConfig = { },
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
            openStore = { },
            refreshConfig = { },
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass,
            uiState = VALIDATING
        )
    }
}