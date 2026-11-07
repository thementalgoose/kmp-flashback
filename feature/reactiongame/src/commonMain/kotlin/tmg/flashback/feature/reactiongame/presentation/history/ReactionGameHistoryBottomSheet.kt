package tmg.flashback.feature.reactiongame.presentation.history

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.reaction_screen_history_title
import flashback.presentation.localisation.generated.resources.reaction_screen_jump_title
import flashback.presentation.localisation.generated.resources.reaction_tier_average
import flashback.presentation.localisation.generated.resources.reaction_tier_good
import flashback.presentation.localisation.generated.resources.reaction_tier_great
import flashback.presentation.localisation.generated.resources.reaction_tier_not_good
import flashback.presentation.localisation.generated.resources.reaction_tier_poor
import flashback.presentation.localisation.generated.resources.reaction_tier_superhuman
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.reactiongame.repositories.ReactionResultTier
import tmg.flashback.feature.reactiongame.repositories.ReactionResultTier.Companion.toTier
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.progressbar.ProgressBar
import tmg.flashback.ui.utils.contrastTextLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReactionGameHistoryBottomSheet(
    show: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    viewModel: ReactionGameHistoryViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { show.value = false },
        containerColor = AppTheme.colors.surface,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = {
            Box(Modifier
                .padding(vertical = AppTheme.dimens.nsmall)
                .clip(RoundedCornerShape(4.dp))
                .background(AppTheme.colors.surfaceInverse)
                .size(width = 50.dp, height = 4.dp)
            )
        },
        content = {
            ReactionGameHistoryBottomSheet(
                dismissed = { show.value = false },
                uiState = uiState.value
            )
        }
    )
}

@Composable
private fun ReactionGameHistoryBottomSheet(
    uiState: ReactionGameHistoryUiState,
    dismissed: () -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .background(AppTheme.colors.surface),
        columns = GridCells.Adaptive(minSize = 250.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
        content = {
            item("header", span = { GridItemSpan(maxLineSpan) }) {
                Header(
                    // Needed for the dialog to show the background colour in XR.
                    modifier = Modifier.background(AppTheme.colors.surface),
                    text = stringResource(string.reaction_screen_history_title),
                    action = null,
                    actionUpClicked = dismissed
                )
            }
            for ((tier, history) in uiState.results) {
                item("key-${tier.ordinal}", span = { GridItemSpan(maxLineSpan) }) {
                    TextBody1(
                        modifier = Modifier
                            .padding(
                                start = AppTheme.dimens.medium,
                                top = AppTheme.dimens.medium,
                                end = AppTheme.dimens.medium
                            ),
                        bold = true,
                        text = stringResource(tier.label)
                    )
                }
                items(history, key = { "content-${it.timeMillis}" }, span = { GridItemSpan(maxLineSpan) }) {
                    CountContainer(
                        count = it.count,
                        content = {
                            ProgressBar(
                                endProgress = (it.timeMillis.toFloat() / 1000f).coerceIn(0f, 1f),
                                modifier = Modifier
                                    .padding(horizontal = AppTheme.dimens.medium)
                                    .height(36.dp),
                                label = { _ -> "${it.timeMillis}ms" },
                                barColor = when (tier) {
                                    ReactionResultTier.SUPERHUMAN -> AppTheme.colors.f1StartLightGreen
                                    ReactionResultTier.EXCEPTIONAL -> AppTheme.colors.f1StartLightGreen
                                    ReactionResultTier.GOOD -> AppTheme.colors.f1StartLightGreen
                                    ReactionResultTier.AVERAGE -> AppTheme.colors.f1StartLightGreen
                                    ReactionResultTier.NOT_GOOD -> AppTheme.colors.f1StartLightGreen
                                    ReactionResultTier.POOR -> AppTheme.colors.f1StartLightAmber
                                }
                            )
                        }
                    )
                }
            }

            if (uiState.jumpStarts > 0) {
                item("key-jumpstart", span = { GridItemSpan(maxLineSpan) }) {
                    TextBody1(
                        modifier = Modifier
                            .padding(
                                start = AppTheme.dimens.medium,
                                top = AppTheme.dimens.medium,
                                end = AppTheme.dimens.medium
                            ),
                        bold = true,
                        text = stringResource(string.reaction_screen_jump_title)
                    )
                }
                item("content-jumpstart", span = { GridItemSpan(maxLineSpan) }) {
                    CountContainer(
                        count = uiState.jumpStarts,
                        content = {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = AppTheme.dimens.medium)
                                    .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
                                    .background(AppTheme.colors.f1StartLightRed)
                                    .fillMaxWidth()
                                    .height(36.dp)
                            )
                        }
                    )
                }
            }
            item("spacer") {
                Spacer(modifier = Modifier
                    .height(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                    .height(AppTheme.dimens.large)
                )
            }
        }
    )
}

@Composable
private fun CountContainer(
    count: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            content()
        }
        TextBody1(
            text = "x${count}",
            modifier = Modifier
                .defaultMinSize(minWidth = 48.dp)
                .padding(end = AppTheme.dimens.medium),
            bold = true,
            textAlign = TextAlign.End
        )
    }
}

private val ReactionResultTier.label: StringResource
    get() = when (this) {
        ReactionResultTier.SUPERHUMAN -> string.reaction_tier_superhuman
        ReactionResultTier.EXCEPTIONAL -> string.reaction_tier_great
        ReactionResultTier.GOOD -> string.reaction_tier_good
        ReactionResultTier.AVERAGE -> string.reaction_tier_average
        ReactionResultTier.NOT_GOOD -> string.reaction_tier_not_good
        ReactionResultTier.POOR -> string.reaction_tier_poor
    }

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        ReactionGameHistoryBottomSheet(
            dismissed = { },
            uiState = ReactionGameHistoryUiState(
                results = listOf(
                    ReactionResultTier.SUPERHUMAN to listOf(
                        History(timeMillis = 350, count = 3)
                    )
                ),
                jumpStarts = 1
            )
        )
    }
}