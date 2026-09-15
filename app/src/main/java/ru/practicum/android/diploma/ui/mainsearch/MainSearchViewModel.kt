package ru.practicum.android.diploma.ui.mainsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.domain.models.VacancySearchParams
import ru.practicum.android.diploma.util.searchDebounce

class MainSearchViewModel(
    private val searchVacancyInteractor: SearchVacancyInteractor
) : ViewModel() {

    private val query = MutableStateFlow("")

    private val _state = MutableStateFlow(MainSearchState())
    val state: StateFlow<MainSearchState> = _state.asStateFlow()

    init {
        query
            .searchDebounce()
            .onEach(::performSearch)
            .launchIn(viewModelScope)
    }

    fun onQueryChanged(newQuery: String) {
        query.value = newQuery
        _state.value = _state.value.copy(query = newQuery)
    }

    fun onQueryCleared() {
        query.value = ""
        _state.value = MainSearchState()
    }

    private fun performSearch(searchText: String) {
        if (searchText.isBlank()) {
            _state.value = _state.value.copy(content = MainSearchContent.Idle)
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(content = MainSearchContent.Loading)
            when (val result = searchVacancyInteractor.searchVacancy(VacancySearchParams(text = searchText))) {
                is Resource.Success -> {
                    val response = result.data
                    _state.value = _state.value.copy(
                        content = if (response.items.isEmpty()) {
                            MainSearchContent.Empty
                        } else {
                            MainSearchContent.Content(
                                vacancies = response.items,
                                found = response.found
                            )
                        }
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        content = if (result.code == -1 || result.code == null) {
                            MainSearchContent.NetworkError
                        } else {
                            MainSearchContent.ServerError
                        }
                    )
                }

                Resource.Loading -> {
                    // no-op
                }
            }
        }
    }
}
