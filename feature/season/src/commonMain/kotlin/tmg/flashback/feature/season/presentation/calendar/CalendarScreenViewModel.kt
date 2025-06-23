package tmg.flashback.feature.season.presentation.calendar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import tmg.flashback.data.repo.repository.SeasonRepository

class CalendarScreenViewModel(
    private val seasonRepository: SeasonRepository
): ViewModel() {

    private var currentSeason: MutableStateFlow<Int> = MutableStateFlow(
        2025
    )

//    val uiState: StateFlow<CalendarScreenState> = currentSeason
//        .flatMapLatest { seasonRepository.getSeason(it) }
//        .map { season ->
//            CalendarScreenState()
//        }

    fun test() {
        seasonRepository.getSeason(2025)
            .map {  }
    }
}