package tmg.flashback.presentation.settings.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_theme_theme_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.presentation.settings.PrefRadio
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.style.theme.Theme
import tmg.flashback.style.theme.Theme.Default
import tmg.flashback.style.theme.Theme.MaterialYou

@Composable
fun SettingsThemeScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsThemeViewModel = koinViewModel()
) {
    ScreenView(screenName = "Settings - Widgets")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsThemeScreen(
        actionUpClicked = actionUpClicked,
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        clickTheme = viewModel::updateSelection
    )
}

@Composable
private fun SettingsThemeScreen(
    uiState: SettingsThemeUIState,
    showBack: Boolean,
    clickTheme: (Theme) -> Unit,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_theme_theme_title),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefRadio(
            item = Settings.Theme.ThemeDefault,
            itemClicked = {
                clickTheme(Default)
            },
            isChecked = uiState.theme == Default
        )
        PrefRadio(
            item = Settings.Theme.ThemeMaterialYou,
            itemClicked = {
                clickTheme(MaterialYou)
            },
            isChecked = uiState.theme == MaterialYou
        )
    }
}