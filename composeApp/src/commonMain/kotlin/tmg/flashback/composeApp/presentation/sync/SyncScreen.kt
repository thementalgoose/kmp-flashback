package tmg.flashback.composeApp.presentation.sync

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowSizeClass
import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.wave
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.splash_continue
import flashback.presentation.localisation.generated.resources.splash_sync_circuits
import flashback.presentation.localisation.generated.resources.splash_sync_config
import flashback.presentation.localisation.generated.resources.splash_sync_constructors
import flashback.presentation.localisation.generated.resources.splash_sync_drivers
import flashback.presentation.localisation.generated.resources.splash_sync_info
import flashback.presentation.localisation.generated.resources.splash_sync_races
import flashback.presentation.localisation.generated.resources.splash_sync_try_again
import flashback.presentation.localisation.generated.resources.splash_title
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import flashback.presentation.localisation.generated.resources.app_name
import flashback.presentation.localisation.generated.resources.error_data_not_available_try_again
import flashback.presentation.localisation.generated.resources.splash_loading
import flashback.presentation.localisation.generated.resources.splash_sync_required_failed
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.composeApp.presentation.sync.SyncState.DONE
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.buttons.ButtonSecondary
import tmg.flashback.style.buttons.ButtonText
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextHeadline1
import kotlin.time.Duration.Companion.milliseconds

private val progressBarHeight = 24.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncBottomSheet(
    hide: () -> Unit,
    windowSizeClass: WindowSizeClass,
) {

    // Disable bottom sheet back / dismiss actions when active, re-enable when done
    val lockBottomSheetOnScreen = remember { mutableStateOf(true) }
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            if (it == SheetValue.Hidden) {
                !lockBottomSheetOnScreen.value
            } else {
                true
            }
        },
    )
    val coroutineScope = rememberCoroutineScope()
    ApplicationTheme {
        ModalBottomSheet(
            onDismissRequest = hide,
            sheetState = sheetState,
            containerColor = AppTheme.colors.surface,
            properties = ModalBottomSheetProperties(
                shouldDismissOnBackPress = !lockBottomSheetOnScreen.value
            )
        ) {
            SyncScreen(
                windowSizeClass = windowSizeClass,
                unlockAndDismiss = {
                    lockBottomSheetOnScreen.value = false
                    coroutineScope.launch {
                        delay(1000.milliseconds)
                        sheetState.hide()
                        hide()
                    }
                }
            )
        }
    }
}

@Composable
fun SyncScreen(
    unlockAndDismiss: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: SyncViewModel = koinViewModel()
) {
    ScreenView(screenName = "Sync")

    val overall = viewModel.overall.collectAsState()

    SyncScreen(
        windowSizeClass = windowSizeClass,
        overall = overall.value,
        tryAgainClicked = viewModel::startInitialSync
    )

    LaunchedEffect(overall.value) {
        if (overall.value == OverallSyncState.Successful) {
            unlockAndDismiss()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.startInitialSync()
    }
}

@Composable
fun SyncScreen(
    windowSizeClass: WindowSizeClass,
    overall: OverallSyncState,
    tryAgainClicked: () -> Unit,
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
                text = stringResource(string.splash_title)
            )
            Image(
                painter = painterResource(Res.drawable.wave),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
            )
        }
        TextBody1(
            text = stringResource(string.splash_sync_info),
            modifier = Modifier.padding(
                top = AppTheme.dimens.small,
                bottom = AppTheme.dimens.large
            )
        )

        LoadingProgress(
            state = overall
        )
        Spinner()

        Spacer(Modifier.height(AppTheme.dimens.medium))

        Controls(
            enableRetry = overall == OverallSyncState.Failed,
            tryAgainClicked = tryAgainClicked,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 80.dp)
        )

        val navigationBarHeight = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
        Spacer(Modifier.height(navigationBarHeight))
    }
}

@Composable
private fun Spinner(
    modifier: Modifier = Modifier,
    intervalMillis: Long = 2000L,
    repeat: Boolean = false
) {
    val messages = listOf(
        stringResource(string.splash_sync_races),
        stringResource(string.splash_sync_circuits),
        stringResource(string.splash_sync_drivers),
        stringResource(string.splash_sync_constructors),
        stringResource(string.app_name),
    )
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(messages, repeat) {
        if (messages.isEmpty()) return@LaunchedEffect

        while (true) {
            delay(intervalMillis.milliseconds)
            if (currentIndex < messages.lastIndex) {
                currentIndex++
            } else if (repeat) {
                currentIndex = 0
            } else {
                break
            }
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        TextBody2(stringResource(string.splash_loading))
        AnimatedContent(
            targetState = currentIndex,
            transitionSpec = {
                // Incoming text slides up from below while fading in;
                // outgoing text slides up and fades out
                (slideInVertically { height -> height / 2 } + fadeIn()) togetherWith
                        (slideOutVertically { height -> -height / 2 } + fadeOut())
            },
            label = "SequentialLoadingTextAnimation"
        ) { targetIndex ->
            TextBody2(messages.getOrElse(targetIndex) { "" })
        }
        TextBody2("...")
    }
}


@Composable
private fun Controls(
    enableRetry: Boolean,
    tryAgainClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = enableRetry,
            content = {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextBody1(
                        text = stringResource(string.splash_sync_required_failed)
                    )
                    ButtonPrimary(
                        text = stringResource(string.splash_sync_try_again),
                        onClick = tryAgainClicked,
                        enabled = enableRetry,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }
}

@Composable
private fun LoadingProgress(
    state: OverallSyncState,
    modifier: Modifier = Modifier,
) {
    val colour = animateColorAsState(
        targetValue = when (state) {
            is OverallSyncState.InProgress -> AppTheme.colors.primary
            OverallSyncState.Successful -> AppTheme.colors.f1DeltaNegative
            OverallSyncState.Failed -> AppTheme.colors.f1DeltaPositive
        },
        animationSpec = tween(durationMillis = 400)
    )
    val progress = animateFloatAsState(
        targetValue = when (state) {
            is OverallSyncState.InProgress -> state.progress
            OverallSyncState.Successful -> 1f
            OverallSyncState.Failed -> 1f
        },
        animationSpec = tween(durationMillis = 400)
    )
    Box(modifier = modifier) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(progressBarHeight),
            color = colour.value,
            trackColor = AppTheme.colors.primaryContainer,
            progress = { progress.value }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewLoading() {
    ApplicationThemePreview {
        SyncScreen(
            overall = OverallSyncState.InProgress(0.3f),
            tryAgainClicked = { },
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewDone() {
    ApplicationThemePreview {
        SyncScreen(
            overall = OverallSyncState.Successful,
            tryAgainClicked = { },
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewFailed() {
    ApplicationThemePreview {
        SyncScreen(
            overall = OverallSyncState.Failed,
            tryAgainClicked = { },
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
        )
    }
}