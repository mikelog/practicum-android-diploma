package ru.practicum.android.diploma.ui.mainsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.domain.models.VacancySearchParams
import ru.practicum.android.diploma.util.searchDebounce

class MainSearchViewModel(
    private val searchVacancyInteractor: SearchVacancyInteractor
) : ViewModel() {

    private val query = MutableStateFlow("")

    private var currentPage = 0
    private var totalPages = 1

    private val _state = MutableStateFlow(MainSearchState())
    val state: StateFlow<MainSearchState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            query
                .searchDebounce()
                .collectLatest(::performSearch)
        }
    }

    fun onQueryChanged(newQuery: String) {
        query.value = newQuery
        _state.value = _state.value.copy(query = newQuery)
    }

    fun onQueryCleared() {
        query.value = ""
        _state.value = MainSearchState()
    }

    fun onListScrolledToEnd() {
        if (_state.value.isNextPageLoading) return
        val content = _state.value.content
        if (content !is MainSearchContent.Content) return
        if (currentPage + 1 >= totalPages) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isNextPageLoading = true)
            val searchTarget = query.value
            val nextPage = currentPage + 1
            when (val result = searchVacancyInteractor.searchVacancy(
                VacancySearchParams(text = searchTarget, page = nextPage)
            )) {
                is Resource.Success -> {
                    currentPage = result.data.page
                    if (searchTarget != query.value) return@launch
                    val deduped = (content.vacancies + result.data.items).distinctBy { it.id }
                    _state.value = _state.value.copy(
                        isNextPageLoading = false,
                        content = MainSearchContent.Content(
                            vacancies = deduped,
                            found = content.found
                        )
                    )
                }

                is Resource.Error -> {
                    if (searchTarget != query.value) return@launch
                    _state.value = _state.value.copy(
                        isNextPageLoading = false,
                        content = result.toMainSearchContent()
                    )
                }

                Resource.Loading -> {
                    // no-op
                }
            }
        }
    }

    private suspend fun performSearch(searchText: String) {
        if (searchText.isBlank()) {
            _state.value = _state.value.copy(content = MainSearchContent.Idle)
            return
        }

        currentPage = 0
        totalPages = 1
        _state.value = _state.value.copy(content = MainSearchContent.Loading, isNextPageLoading = false)

        when (val result = searchVacancyInteractor.searchVacancy(
            VacancySearchParams(text = searchText, page = 0)
        )) {
            is Resource.Success -> {
                val response = result.data
                currentPage = response.page
                totalPages = response.pages
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
                _state.value = _state.value.copy(content = result.toMainSearchContent())
            }

            Resource.Loading -> {
                // no-op
            }
        }
    }

    private fun Resource.Error.toMainSearchContent(): MainSearchContent =
        if (code == -1 || code == null) MainSearchContent.NetworkError
        else MainSearchContent.ServerError
}