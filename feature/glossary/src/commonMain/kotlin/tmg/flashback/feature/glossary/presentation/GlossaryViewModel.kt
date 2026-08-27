package tmg.flashback.feature.glossary.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class GlossaryViewModel(
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {

    private val searchTerm: MutableStateFlow<String?> = MutableStateFlow(null)
    private val entries: MutableStateFlow<List<Entry>> = MutableStateFlow(listOf())

    private val _uiState: MutableStateFlow<GlossaryUiState> = MutableStateFlow(
        GlossaryUiState()
    )
    val uiState: StateFlow<GlossaryUiState> = _uiState

    fun enterSearchTerm(search: String) {
        searchTerm.update { search }
    }
}