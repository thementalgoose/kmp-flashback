package tmg.flashback.feature.paddock.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_paddock
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.navigation.Screen
import tmg.flashback.ui.components.header.Header

@Composable
fun PaddockScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowSizeClass,
    navigateTo: (Screen) -> Unit,
    viewModel: PaddockViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    PaddockScreen(
        paddingValues = paddingValues,
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