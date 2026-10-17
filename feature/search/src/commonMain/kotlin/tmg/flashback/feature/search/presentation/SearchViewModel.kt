@file:OptIn(ExperimentalCoroutinesApi::class)

package tmg.flashback.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.CircuitRepository
import tmg.flashback.data.repo.repository.ConstructorRepository
import tmg.flashback.data.repo.repository.DriverRepository

class SearchViewModel(
    private val circuitRepository: CircuitRepository,
    private val driversRepository: DriverRepository,
    private val constructorRepository: ConstructorRepository
): ViewModel() {

    private val searchUiState = SearchUiState(
        categories = Category.entries.sorted(),
        searchTerm = "",
        isLoading = false,
        resultsDrivers = emptyList(),
        resultsConstructors = emptyList(),
        resultsCircuits = emptyList()
    )

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val searchTerm: MutableStateFlow<String> = MutableStateFlow("")
    private val filtering: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())

    val uiState: StateFlow<SearchUiState> =
        combine(
            circuitRepository.getCircuits().map {
                    list -> list.map { Result.ResultCircuit(it) }
            },
            driversRepository.getDrivers().map {
                list -> list.map { Result.ResultDriver(it) }
            },
            constructorRepository.getConstructors().map {
                    list -> list.map { Result.ResultTeam(it) }
            },
            searchTerm.asFlow(),
            filtering.asFlow(),
            isLoading.asFlow()
        ) { arr ->
            val circuits = arr[0] as List<Result>
            val drivers = arr[1] as List<Result>
            val constructors = arr[2] as List<Result>
            val search = arr[3] as String
            val filtering = arr[4] as List<Category>
            val isLoading = arr[5] as Boolean
            SearchUiState(
                categories = filtering.sorted(),
                searchTerm = search,
                isLoading = isLoading,
                resultsDrivers = when (filtering.contains(Category.Drivers)) {
                    true -> drivers.filter(search)
                    false -> emptyList()
                },
                resultsCircuits = when (filtering.contains(Category.Circuits)) {
                    true -> circuits.filter(search)
                    false -> emptyList()
                },
                resultsConstructors = when (filtering.contains(Category.Teams)) {
                    true -> constructors.filter(search)
                    false -> emptyList()
                },
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, searchUiState)

    fun searchTerm(search: String) {
        this.searchTerm.update { search }
    }

    fun applyFilters(categories: List<Category>) {
        this.filtering.update { categories }
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading.update { true }
            circuitRepository.populateCircuits()
            driversRepository.populateDrivers()
            constructorRepository.populateConstructors()
            isLoading.update { false }
        }
    }

    private fun List<Result>.filter(searchTerm: String): List<Result> {
        return this.filter {
            it.search.contains(searchTerm.lowercase())
        }
    }
    private fun List<Category>.sorted(): List<Category> = this.sortedBy { it.ordinal }
}