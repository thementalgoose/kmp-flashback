@file:OptIn(ExperimentalComposeUiApi::class)

package tmg.flashback.feature.reactiongame.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.reaction_screen_action_again
import flashback.presentation.localisation.generated.resources.reaction_screen_action_react
import flashback.presentation.localisation.generated.resources.reaction_screen_action_reset
import flashback.presentation.localisation.generated.resources.reaction_screen_action_start
import flashback.presentation.localisation.generated.resources.reaction_screen_heading
import flashback.presentation.localisation.generated.resources.reaction_screen_instructions
import flashback.presentation.localisation.generated.resources.reaction_screen_jump_subtitle
import flashback.presentation.localisation.generated.resources.reaction_screen_jump_title
import flashback.presentation.localisation.generated.resources.reaction_screen_miss_subtitle
import flashback.presentation.localisation.generated.resources.reaction_screen_miss_title
import flashback.presentation.localisation.generated.resources.reaction_screen_subtitle
import flashback.presentation.localisation.generated.resources.reaction_screen_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.style.AppTheme
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextHeadline1
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.progressbar.ProgressBar

const val fadeTransitionDurationMs = 300

@Composable
fun ReactionGameScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: ReactionGameViewModel = koinViewModel()
) {
    ScreenView(screenName = "Reaction Game")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    ReactionScreen(
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        state = uiState.value,
        start = viewModel::start,
        react = viewModel::react,
        reset = viewModel::reset
    )

    val result = uiState.value as? ReactionUiState.Game
    val lightsOut = result?.hasDisplayedSequence == true && result.lights == 0
    LaunchedEffect(lightsOut) {
        if (lightsOut) {
            viewModel.setLightsOutTime()
        }
    }
}

@Composable
private fun ReactionScreen(
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    state: ReactionUiState,
    start: () -> Unit,
    reset: () -> Unit,
    react: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .pointerInput(state is ReactionUiState.Game) {
                detectTapGestures(
                    onPress = {
                        when (state) {
                            is ReactionUiState.Game -> react()
                            else -> {}
                        }
                    }
                )
            }
            .background(AppTheme.colors.surface),
    ) {
        Header(
            text = stringResource(string.reaction_screen_title),
            action = when (windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT) {
                true -> null
                false -> HeaderAction.MENU
            },
            actionUpClicked = actionUpClicked
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .background(AppTheme.colors.surface)
        ) {

            if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        RaceStartLights(
                            modifier = Modifier.padding(AppTheme.dimens.medium),
                            state = state
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(AppTheme.dimens.medium)
                            .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                            .background(AppTheme.colors.surfaceContainer3)
                            .padding(AppTheme.dimens.medium),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
                    ) {
                        AnimatedContent(
                            targetState = state,
                            transitionSpec = {
                                fadeIn(tween(fadeTransitionDurationMs)) togetherWith fadeOut(
                                    tween(
                                        fadeTransitionDurationMs
                                    )
                                )
                            },
                            label = "Content"
                        ) {
                            when (it) {
                                is ReactionUiState.Game -> Game()
                                ReactionUiState.JumpStart -> JumpStart()
                                ReactionUiState.Missed -> Missed()
                                is ReactionUiState.Results -> Results(it)
                                ReactionUiState.Start -> Start()
                            }
                        }
                        Action(
                            modifier = Modifier.fillMaxWidth(),
                            state = state,
                            reset = reset,
                            react = react,
                            start = start,
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        RaceStartLights(
                            modifier = Modifier.padding(AppTheme.dimens.medium),
                            state = state
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(AppTheme.dimens.medium)
                            .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                            .background(AppTheme.colors.surfaceContainer3)
                            .padding(AppTheme.dimens.medium),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
                    ) {
                        val modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                        when (state) {
                            is ReactionUiState.Game -> Game(modifier)
                            ReactionUiState.JumpStart -> JumpStart(modifier)
                            ReactionUiState.Missed -> Missed(modifier)
                            is ReactionUiState.Results -> Results(state, modifier)
                            ReactionUiState.Start -> Start(modifier)
                        }
                        Action(
                            modifier = Modifier.fillMaxWidth(),
                            state = state,
                            reset = reset,
                            react = react,
                            start = start,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Action(
    state: ReactionUiState,
    reset: () -> Unit,
    start: () -> Unit,
    react: () -> Unit,
    modifier: Modifier = Modifier
) {
    ButtonPrimary(
        modifier = modifier,
        onClick = {
            when (state) {
                ReactionUiState.Start -> start()
                is ReactionUiState.Game -> react()
                ReactionUiState.JumpStart,
                ReactionUiState.Missed,
                is ReactionUiState.Results -> reset()
            }
        },
        text = when (state) {
            is ReactionUiState.Game -> stringResource(string.reaction_screen_action_react)
            is ReactionUiState.Results -> stringResource(string.reaction_screen_action_again)
            ReactionUiState.JumpStart -> stringResource(string.reaction_screen_action_reset)
            ReactionUiState.Missed -> stringResource(string.reaction_screen_action_reset)
            ReactionUiState.Start -> stringResource(string.reaction_screen_action_start)
        }
    )
}

@Composable
private fun Start(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
    ) {
        TextTitle(
            bold = true,
            modifier = Modifier,
            text = stringResource(string.reaction_screen_heading)
        )
        TextBody1(
            modifier = Modifier,
            text = stringResource(string.reaction_screen_subtitle)
        )
    }
}

@Composable
private fun Results(
    result: ReactionUiState.Results,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
    ) {
        TextHeadline1(
            modifier = Modifier,
            text = "${result.timeMillis}ms"
        )
        val label = stringResource(result.tier.label)
        ProgressBar(
            endProgress = result.percentage,
            modifier = Modifier.height(48.dp),
            label = { label }
        )
        TextBody1(
            modifier = Modifier,
            text = stringResource(result.tier.desc)
        )
    }
}

@Composable
private fun Missed(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
    ) {
        TextTitle(
            bold = true,
            modifier = Modifier,
            text = stringResource(string.reaction_screen_miss_title)
        )
        TextBody1(
            modifier = Modifier,
            text = stringResource(string.reaction_screen_miss_subtitle)
        )
    }
}

@Composable
private fun JumpStart(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
    ) {
        TextTitle(
            bold = true,
            modifier = Modifier,
            text = stringResource(string.reaction_screen_jump_title)
        )
        TextBody1(
            modifier = Modifier,
            text = stringResource(string.reaction_screen_jump_subtitle)
        )
    }
}

@Composable
private fun Game(
    modifier: Modifier = Modifier
) {
    TextBody1(
        modifier = modifier,
        text = stringResource(string.reaction_screen_instructions)
    )
}

@Composable
private fun RaceStartLights(
    state: ReactionUiState,
    modifier: Modifier = Modifier
) {
    RaceStartLights(
        modifier = modifier,
        state = when (state) {
            is ReactionUiState.Game -> StartLightState.StartSequence(state.lights)
            ReactionUiState.JumpStart -> StartLightState.AbortedStart
            ReactionUiState.Missed -> StartLightState.AbortedStart
            is ReactionUiState.Results -> StartLightState.StartSequence(0)
            ReactionUiState.Start -> StartLightState.Ready
        },
        panelType = LightPanel.HALF_HEIGHT
    )
}