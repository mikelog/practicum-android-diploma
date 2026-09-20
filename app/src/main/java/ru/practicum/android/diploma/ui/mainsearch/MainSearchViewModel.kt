package ru.practicum.android.diploma.ui.mainsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
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

    private val _errorToast = MutableSharedFlow<Int>()
    val errorToast: SharedFlow<Int> = _errorToast.asSharedFlow()

    private val _searchStarted = MutableSharedFlow<Unit>()
    val searchStarted: SharedFlow<Unit> = _searchStarted.asSharedFlow()

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
        if (content !is MainSearchContent.Content || currentPage + 1 >= totalPages) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isNextPageLoading = true)
            loadNextPage(query.value, currentPage + 1, content)
        }
    }

    private suspend fun loadNextPage(
        searchText: String,
        nextPage: Int,
        currentContent: MainSearchContent.Content,
    ) {
        when (val result = searchVacancyInteractor.searchVacancy(
            VacancySearchParams(text = searchText, page = nextPage)
        )) {
            is Resource.Success -> {
                if (searchText != query.value) {
                    _state.value = _state.value.copy(isNextPageLoading = false)
                    return
                }
                currentPage = result.data.page
                val deduped = (currentContent.vacancies + result.data.items).distinctBy { it.id }.toImmutableList()
                _state.value = _state.value.copy(
                    isNextPageLoading = false,
                    content = MainSearchContent.Content(
                        vacancies = deduped,
                        found = currentContent.found
                    )
                )
            }

            is Resource.Error -> {
                if (searchText != query.value) {
                    _state.value = _state.value.copy(isNextPageLoading = false)
                    return
                }
                _state.value = _state.value.copy(isNextPageLoading = false)
                _errorToast.emit(result.toErrorMessageRes())
            }

            Resource.Loading -> Unit
        }
    }

    private suspend fun performSearch(searchText: String) {
        if (searchText.isBlank()) {
            _state.value = _state.value.copy(content = MainSearchContent.Idle)
            return
        }

        _searchStarted.emit(Unit)
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
                            vacancies = response.items.toImmutableList(),
                            found = response.found
                        )
                    }
                )
            }

            is Resource.Error -> {
                _state.value = _state.value.copy(content = result.toMainSearchContent())
            }

            Resource.Loading -> Unit
        }
    }

    private fun Resource.Error.toMainSearchContent(): MainSearchContent =
        if (code == -1 || code == null) {
            MainSearchContent.NetworkError
        } else {
            MainSearchContent.ServerError
        }

    private fun Resource.Error.toErrorMessageRes(): Int =
        if (code == -1 || code == null) {
            R.string.network_error_message
        } else {
            R.string.server_error_message
        }
}