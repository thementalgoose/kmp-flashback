package tmg.flashback.composeApp.presentation.settings.widgets

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.widgets.upnext.repositories.UpNextWidgetRepository
import tmg.flashback.widgets.upnext.usecases.AddWidgetUseCase

class SettingsWidgetsViewModel(
    private val widgetsRepository: UpNextWidgetRepository,
    private val addWidgetUseCase: AddWidgetUseCase,
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsWidgetsUiState> = MutableStateFlow(
        SettingsWidgetsUiState(
            showBackground = widgetsRepository.showBackground,
            linkToEvent = widgetsRepository.deeplinkToEvent,
            showWeather = widgetsRepository.showWeather,
            isAddWidgetSupported = addWidgetUseCase.isSupported,
        ))
    val uiState: StateFlow<SettingsWidgetsUiState> = _uiState

    fun refresh() {
        _uiState.update {
            SettingsWidgetsUiState(
                showBackground = widgetsRepository.showBackground,
                linkToEvent = widgetsRepository.deeplinkToEvent,
                showWeather = widgetsRepository.showWeather,
                isAddWidgetSupported = addWidgetUseCase.isSupported,
            )
        }
    }

    fun addWidget() {
        addWidgetUseCase()
    }

    fun updateShowBackground(enabled: Boolean) {
        widgetsRepository.showBackground = enabled
        refresh()
    }

    fun updateDeeplinkToEvent(enabled: Boolean) {
        widgetsRepository.deeplinkToEvent = enabled
        refresh()
    }

    fun updateShowWeather(enabled: Boolean) {
        widgetsRepository.showWeather = enabled
        refresh()
    }
}