package tmg.flashback.presentation.settings.darkmode

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_theme_nightmode_dark
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.presentation.settings.PrefRadio
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.style.theme.NightMode
import tmg.flashback.style.theme.NightMode.DAY
import tmg.flashback.style.theme.NightMode.DEFAULT
import tmg.flashback.style.theme.NightMode.NIGHT

@Composable
fun SettingsDarkModeScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsDarkModeViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsDarkModeScreen(
        actionUpClicked = actionUpClicked,
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        clickNightMode = viewModel::updateSelection
    )
}

@Composable
private fun SettingsDarkModeScreen(
    uiState: SettingsDarkModeUiState,
    showBack: Boolean,
    clickNightMode: (NightMode) -> Unit,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_theme_nightmode_dark),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefRadio(
            item = Settings.DarkMode.DarkModeAuto,
            itemClicked = {
                clickNightMode(DEFAULT)
            },
            isChecked = uiState.nightMode == DEFAULT
        )
        PrefRadio(
            item = Settings.DarkMode.DarkModeLight,
            itemClicked = {
                clickNightMode(DAY)
            },
            isChecked = uiState.nightMode == DAY
        )
        PrefRadio(
            item = Settings.DarkMode.DarkModeDark,
            itemClicked = {
                clickNightMode(NIGHT)
            },
            isChecked = uiState.nightMode == NIGHT
        )
    }
}