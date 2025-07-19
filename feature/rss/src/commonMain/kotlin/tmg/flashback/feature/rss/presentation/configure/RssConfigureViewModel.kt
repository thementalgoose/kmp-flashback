package tmg.flashback.feature.rss.presentation.configure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.repositories.RssRepository
import tmg.flashback.feature.rss.usecases.GetSourcesUseCase
import tmg.flashback.infrastructure.log.logInfo

class RssConfigureViewModel(
    private val rssRepository: RssRepository,
    private val getSourcesUseCase: GetSourcesUseCase,
    private val openWebpageUseCase: OpenWebpageUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<RssConfigureUiState> = MutableStateFlow(
        RssConfigureUiState(
            sources = getSources(),
            showDescription = rssRepository.rssShowDescription,
            showAddCustom = rssRepository.isAddCustomSourcesEnabled
        ))
    val uiState: StateFlow<RssConfigureUiState> = _uiState

    private fun getSources(): List<ConfiguredSupportedSource> {
        val rssUrls = rssRepository.rssUrls
        val list = getSourcesUseCase()
        return list.map {
            ConfiguredSupportedSource(it, rssUrls.contains(it.rssLink))
        }
    }

    fun updateSource(source: String, include: Boolean) {
        if (source.trim().isBlank() || source.trim().isEmpty()) {
            return
        }
        if (source.contains("|")) {
            return
        }
        when (include) {
            true -> rssRepository.rssUrls += source.trim()
            false -> rssRepository.rssUrls -= source.trim()
        }
        _uiState.update { it.copy(sources = getSources()) }
    }

    fun clickContactLink(source: SupportedSource) {
        openWebpageUseCase.invoke(source.contactLink)
    }

    fun updateShowDescription(enabled: Boolean) {
        rssRepository.rssShowDescription = enabled
        _uiState.update { it.copy(showDescription = enabled) }
    }
}