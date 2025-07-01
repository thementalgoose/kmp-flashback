package tmg.flashback.presentation.settings.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_home
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.presentation.settings.PrefSwitch
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction


@Composable
fun SettingsLayoutScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsLayoutViewModel = koinViewModel(),
) {
    ScreenView(screenName = "Settings - Layout")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsLayoutScreen(
        actionUpClicked = actionUpClicked,
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        updateCollapseRaces = viewModel::updateCollapseRacesEnabled,
        updateShowEmptyWeeks = viewModel::updateShowEmptyWeeks,
        updateKeepLastSeason = viewModel::updateKeepLastSeason
    )
}

@Composable
private fun SettingsLayoutScreen(
    uiState: SettingsLayoutUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    updateCollapseRaces: (Boolean) -> Unit,
    updateShowEmptyWeeks: (Boolean) -> Unit,
    updateKeepLastSeason: (Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_home),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefSwitch(
            item = Settings.Layout.RecentHighlights,
            isChecked = uiState.recentHighlights,
            itemClicked = { },
        )
        PrefSwitch(
            item = Settings.Layout.CollapsibleRaces,
            isChecked = uiState.collapseRaces,
            itemClicked = { updateCollapseRaces(!uiState.collapseRaces) },
        )
        PrefSwitch(
            item = Settings.Layout.EmptyWeeks,
            isChecked = uiState.showEmptyWeeks,
            itemClicked = { updateShowEmptyWeeks(!uiState.showEmptyWeeks) },
        )
        PrefSwitch(
            item = Settings.Layout.KeepSeason,
            isChecked = uiState.keepLastSeason,
            itemClicked = { updateKeepLastSeason(!uiState.keepLastSeason) },
        )
    }
}