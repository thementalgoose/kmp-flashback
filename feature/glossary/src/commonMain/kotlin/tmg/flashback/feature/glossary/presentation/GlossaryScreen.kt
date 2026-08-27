package tmg.flashback.feature.glossary.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GlossaryScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    navigateTo: (NavKey) -> Unit,
    viewModel: GlossaryViewModel = koinViewModel()
) {

}