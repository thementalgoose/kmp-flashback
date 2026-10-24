package tmg.flashback.composeApp.presentation.paddock

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_paddock
import flashback.presentation.localisation.generated.resources.nav_reaction_game
import flashback.presentation.localisation.generated.resources.nav_rss
import flashback.presentation.localisation.generated.resources.nav_search
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSearch
import tmg.flashback.navigation.Screen
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.navigation.appBarMaximumHeight

@Composable
fun PaddockScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowSizeClass,
    navigateTo: (Screen) -> Unit,
    viewModel: PaddockViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val insets = when (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        true -> paddingValues
        false -> {
            val direction = LocalLayoutDirection.current
            PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + appBarMaximumHeight,
                start = paddingValues.calculateStartPadding(direction),
                end = paddingValues.calculateEndPadding(direction)
            )
        }
    }

    PaddockScreen(
        paddingValues = insets,
        windowSizeClass = windowSizeClass,
        navigateTo = navigateTo,
        uiState = uiState.value
    )
}

@Composable
fun PaddockScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowSizeClass,
    navigateTo: (Screen) -> Unit,
    uiState: PaddockUiState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingValues
    ) {
        item(key = "header") {
            Header(
                text = stringResource(string.nav_paddock),
                action = null
            )
        }
        if (uiState.isRssEnabled) {
            item(key = "rss") {
                ButtonPrimary(
                    text = stringResource(string.nav_rss),
                    onClick = {
                        navigateTo(NavRss)
                    }
                )
            }
        }
        item(key = "reaction_game") {
            ButtonPrimary(
                text = stringResource(string.nav_reaction_game),
                onClick = {
                    navigateTo(NavReactionGame)
                }
            )
        }
        item(key = "search") {
            ButtonPrimary(
                text = stringResource(string.nav_search),
                onClick = {
                    navigateTo(NavSearch())
                }
            )
        }
    }
}