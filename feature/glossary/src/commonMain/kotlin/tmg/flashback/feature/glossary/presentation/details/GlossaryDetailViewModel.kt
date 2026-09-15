package tmg.flashback.feature.glossary.presentation.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel
import tmg.flashback.infrastructure.extensions.toEnum
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@KoinViewModel
class GlossaryDetailViewModel(
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {
    private val _uiState: MutableStateFlow<GlossaryDetailUiState> = MutableStateFlow(
        GlossaryDetailUiState())
    val uiState: StateFlow<GlossaryDetailUiState> = _uiState

    fun load(id: String) {
        _uiState.update {
            it.copy(glossary = id.toEnum { it.id })
        }
    }
}
