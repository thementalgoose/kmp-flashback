package tmg.flashback.presentation.sync

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowSizeClass
import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.wave
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.splash_sync_circuits
import flashback.presentation.localisation.generated.resources.splash_sync_config
import flashback.presentation.localisation.generated.resources.splash_sync_constructors
import flashback.presentation.localisation.generated.resources.splash_sync_drivers
import flashback.presentation.localisation.generated.resources.splash_sync_info
import flashback.presentation.localisation.generated.resources.splash_sync_races
import flashback.presentation.localisation.generated.resources.splash_sync_try_again
import flashback.presentation.localisation.generated.resources.splash_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextHeadline1


@Composable
fun SyncBottomSheet(
    unlock: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: SyncViewModel = koinViewModel()
) {
    val stateRaces = viewModel.races.collectAsState()
    val stateDrivers = viewModel.drivers.collectAsState()
    val stateConstructors = viewModel.constructors.collectAsState()
    val stateCircuits = viewModel.circuits.collectAsState()
    val stateConfig = viewModel.config.collectAsState()

    val overall = viewModel.overall.collectAsState()

    SyncBottomSheet(
        windowSizeClass = windowSizeClass,
        drivers = stateDrivers.value,
        circuits = stateCircuits.value,
        constructors = stateConstructors.value,
        races = stateRaces.value,
        config = stateConfig.value,
        showTryAgain = overall.value == SyncState.FAILED,
        tryAgainClicked = viewModel::startInitialSync
    )

    LaunchedEffect(overall.value) {
        if (overall.value == SyncState.DONE) {
            unlock()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.startInitialSync()
    }
}

@Composable
fun SyncBottomSheet(
    windowSizeClass: WindowSizeClass,
    drivers: SyncState,
    circuits: SyncState,
    config: SyncState,
    constructors: SyncState,
    races: SyncState,
    showTryAgain: Boolean,
    tryAgainClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.dimens.medium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {
        Row(
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

        Breakdown(
            label = string.splash_sync_drivers,
            syncState = drivers
        )
        Breakdown(
            label = string.splash_sync_constructors,
            syncState = constructors
        )
        Breakdown(
            label = string.splash_sync_circuits,
            syncState = circuits
        )
        Breakdown(
            label = string.splash_sync_races,
            syncState = races
        )
        Breakdown(
            label = string.splash_sync_config,
            syncState = config
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.dimens.xsmall)
                .defaultMinSize(minHeight = 100.dp),
            horizontalAlignment = Alignment.End
        ) {
            if (showTryAgain) {
                ButtonPrimary(
                    text = stringResource(string.splash_sync_try_again),
                    onClick = tryAgainClicked
                )
            }
        }

        val navigationBarHeight = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
        Spacer(Modifier.height(navigationBarHeight))
    }
}

private val progressBarHeight = 24.dp

@Composable
private fun Breakdown(
    label: StringResource,
    syncState: SyncState
) {
    val colour = animateColorAsState(
        targetValue = when (syncState) {
            SyncState.LOADING -> AppTheme.colors.primary
            SyncState.DONE -> AppTheme.colors.f1DeltaNegative
            SyncState.FAILED -> AppTheme.colors.f1DeltaPositive
        },
        animationSpec = tween(durationMillis = 400)
    )
    val progress = animateFloatAsState(
        targetValue = when (syncState) {
            SyncState.LOADING -> 0f
            SyncState.DONE -> 1f
            SyncState.FAILED -> 1f
        },
        animationSpec = tween(durationMillis = 400)
    )
    val textColor = animateColorAsState(
        targetValue = when (syncState) {
            SyncState.LOADING -> AppTheme.colors.onPrimaryContainer
            SyncState.DONE -> Color(0xFF383838)
            SyncState.FAILED -> Color(0xFFF8F8F8)
        },
        animationSpec = tween(durationMillis = 400)
    )

    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        Box(Modifier.fillMaxWidth()) {
            LinearProgressIndicator(
                color = colour.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(progressBarHeight)
                    .alpha(0.5f)
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(progressBarHeight),
                color = colour.value,
                trackColor = AppTheme.colors.primaryContainer,
                progress = { progress.value }
            )
            BreakdownLabel(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(horizontal = AppTheme.dimens.small),
                text = stringResource(resource = label).uppercase(),
                textColor = textColor.value
            )
        }

    }
}

@Composable
private fun BreakdownLabel(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text,
        letterSpacing = 2.sp,
        maxLines = 1,
        modifier = modifier,
        style = AppTheme.typography.block.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.W900,
            color = textColor
        )
    )
}

@Preview
@Composable
private fun PreviewLoading(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SyncBottomSheet(
            drivers = SyncState.LOADING,
            circuits = SyncState.LOADING,
            config = SyncState.DONE,
            constructors = SyncState.DONE,
            races = SyncState.LOADING,
            showTryAgain = false,
            tryAgainClicked = { },
            windowSizeClass = WindowSizeClass.compute(400f, 700f)
        )
    }
}

@Preview
@Composable
private fun PreviewFailed(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SyncBottomSheet(
            drivers = SyncState.DONE,
            circuits = SyncState.LOADING,
            config = SyncState.FAILED,
            constructors = SyncState.LOADING,
            races = SyncState.LOADING,
            showTryAgain = true,
            tryAgainClicked = { },
            windowSizeClass = WindowSizeClass.compute(400f, 700f)
        )
    }
}