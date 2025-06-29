package tmg.flashback.feature.constructors.presentation.season

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import tmg.flashback.data.repo.repository.ConstructorRepository

class ConstructorSeasonViewModel(
    private val constructorRepository: ConstructorRepository
): ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

//    private val uiState: StateFlow<ConstructorSeasonUiState> = constructorRepository
//        .getConstructorOverview()
//        .map {
//
//        }
}