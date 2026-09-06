package tmg.flashback.composeApp.presentation.settings.layout_home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_section_home_title
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.composeApp.presentation.settings.PrefLink
import tmg.flashback.composeApp.presentation.settings.PrefSwitch
import tmg.flashback.composeApp.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction


@Composable
fun SettingsLayoutHomeScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsLayoutHomeViewModel = koinViewModel(),
) {
    ScreenView(screenName = "Settings - Layout Home")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsLayoutHomeScreen(
        actionUpClicked = actionUpClicked,
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        updateHighlights = viewModel::updateHighlight,
        updateCollapseRaces = viewModel::updateCollapseRacesEnabled,
        updateShowEmptyWeeks = viewModel::updateShowEmptyWeeks,
        updateKeepLastSeason = viewModel::updateKeepLastSeason
    )
}

@Composable
private fun SettingsLayoutHomeScreen(
    uiState: SettingsLayoutHomeUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    updateHighlights: (Boolean) -> Unit,
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
                text = stringResource(string.settings_section_home_title),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefLink(
            item = Settings.LayoutHome.Timezone(TimeZone.currentSystemDefault().id),
            itemClicked = { },
        )
        PrefSwitch(
            item = Settings.LayoutHome.RecentHighlights,
            isChecked = uiState.recentHighlights,
            itemClicked = { updateHighlights(!uiState.recentHighlights) },
        )
        PrefSwitch(
            item = Settings.LayoutHome.CollapsibleRaces,
            isChecked = uiState.collapseRaces,
            itemClicked = { updateCollapseRaces(!uiState.collapseRaces) },
        )
        PrefSwitch(
            item = Settings.LayoutHome.EmptyWeeks,
            isChecked = uiState.showEmptyWeeks,
            itemClicked = { updateShowEmptyWeeks(!uiState.showEmptyWeeks) },
        )
        PrefSwitch(
            item = Settings.LayoutHome.KeepSeason,
            isChecked = uiState.keepLastSeason,
            itemClicked = { updateKeepLastSeason(!uiState.keepLastSeason) },
        )
    }
}