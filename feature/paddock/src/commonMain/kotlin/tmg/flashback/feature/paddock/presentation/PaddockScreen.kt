package tmg.flashback.feature.paddock.presentation

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
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.navigation.Screen
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
private fun PaddockScreen(
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
                actionUpClicked = { },
                action = null
            )
        }
    }
}