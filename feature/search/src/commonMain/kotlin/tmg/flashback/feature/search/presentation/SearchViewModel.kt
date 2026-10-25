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
        selectedCategory = null,
        searchTerm = "",
        isLoading = false,
        results = emptyList()
    )

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val searchTerm: MutableStateFlow<String> = MutableStateFlow("")
    private val filtering: MutableStateFlow<Category?> = MutableStateFlow(null)

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
            val circuits = arr[0] as List<Result.ResultCircuit>
            val drivers = arr[1] as List<Result.ResultDriver>
            val constructors = arr[2] as List<Result.ResultTeam>
            val search = arr[3] as String
            val filtering = arr[4] as? Category?
            val isLoading = arr[5] as Boolean

            val resultsDrivers = when (filtering == Category.Drivers) {
                true -> drivers.filter(search)
                false -> emptyList()
            }
            val resultsCircuits = when (filtering == Category.Circuits) {
                true -> circuits.filter(search)
                false -> emptyList()
            }
            val resultsConstructors = when (filtering == Category.Teams) {
                true -> constructors.filter(search)
                false -> emptyList()
            }

            val results = resultsDrivers + resultsConstructors + resultsCircuits

            SearchUiState(
                selectedCategory = filtering,
                searchTerm = search,
                isLoading = isLoading,
                results = results
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, searchUiState)

    fun searchTerm(search: String) {
        this.searchTerm.update { search }
    }

    fun applyFilter(category: Category?) {
        this.filtering.update { category }
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

    private fun <T: Result> List<T>.filter(searchTerm: String): List<T> {
        return this.filter {
            it.search.contains(searchTerm.lowercase())
        }
    }
    private fun List<Category>.sorted(): List<Category> = this.sortedBy { it.ordinal }
}