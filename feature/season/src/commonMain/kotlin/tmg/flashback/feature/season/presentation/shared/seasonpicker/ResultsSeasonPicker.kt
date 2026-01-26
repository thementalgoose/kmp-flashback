package tmg.flashback.feature.season.presentation.shared.seasonpicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.dashboard_new_season_available
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.Badge
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextHeadline1
import tmg.flashback.ui.components.season.PickerItem
import tmg.flashback.ui.components.season.Picker

@Composable
fun ResultsSeasonPicker(
    subtitle: String?,
    viewModel: SeasonPickerViewModel = koinViewModel()
) {
    val seasons = viewModel.supportedSeasons.collectAsState()
    val currentSeason = viewModel.currentSeason.collectAsState()
    val newSeasonAvailable = viewModel.newSeasonAvailable.collectAsState()
    ResultsSeasonPicker(
        subtitle = subtitle,
        currentSeason = currentSeason.value,
        supportedSeasons = seasons.value,
        newSeasonAvailable = newSeasonAvailable.value,
        currentSeasonUpdated = viewModel::currentSeasonUpdate
    )
}

@Composable
fun ResultsSeasonPicker(
    subtitle: String?,
    currentSeason: Int,
    supportedSeasons: List<Int>,
    newSeasonAvailable: Boolean,
    currentSeasonUpdated: (season: Int) -> Unit
) {
    val seasons = remember(supportedSeasons) { supportedSeasons.map { PickerItem.Text(it.toString()) } }
    Column(
        modifier = Modifier
            .padding(vertical = AppTheme.dimens.medium)
            .fillMaxWidth()
    ) {
        Picker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.medium),
            option = PickerItem.Text(currentSeason.toString()),
            optionsToShow = seasons,
            optionUpdated = {
                if (it is PickerItem.Text) {
                    currentSeasonUpdated(it.text.toInt())
                }
            },
            labelContent = {
                if (newSeasonAvailable) {
                    BadgeView(model = Badge(label = stringResource(resource = string.dashboard_new_season_available)))
                }
            }
        )
        if (subtitle != null) {
            TextHeadline1(
                text = subtitle,
                modifier = Modifier.padding(horizontal = AppTheme.dimens.medium)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWithNewSeason(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        ResultsSeasonPicker(
            subtitle = "Subtitle",
            currentSeason = 2023,
            supportedSeasons = listOf(2023, 2024),
            newSeasonAvailable = true,
            currentSeasonUpdated = { }
        )
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        ResultsSeasonPicker(
            subtitle = "Subtitle",
            currentSeason = 2023,
            supportedSeasons = listOf(2023, 2024),
            newSeasonAvailable = false,
            currentSeasonUpdated = { }
        )
    }
}