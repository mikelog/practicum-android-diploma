package ru.practicum.android.diploma.ui.mainsearch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.domain.api.SearchVacancyInteractor

class MainSearchViewModel(
    private val searchVacancyInteractor: SearchVacancyInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(MainSearchState())
    val state: StateFlow<MainSearchState> = _state.asStateFlow()

    fun onQueryChanged(query: String) {
        _state.value = _state.value.copy(query = query)
    }

    fun onClearQuery() {
        _state.value = _state.value.copy(query = "", content = MainSearchContent.Idle)
    }
}
