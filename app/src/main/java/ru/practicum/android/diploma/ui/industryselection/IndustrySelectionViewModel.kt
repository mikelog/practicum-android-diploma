package ru.practicum.android.diploma.ui.industryselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustry

class IndustrySelectionViewModel(
    private val industryInteractor: IndustryInteractor,
    filterSettingsInteractor: FilterSettingsInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow(
        IndustrySelectionState(selected = filterSettingsInteractor.get().industry)
    )

    val state: StateFlow<IndustrySelectionState> = _state.asStateFlow()

    // Полный список с сервера: API не умеет искать, поэтому фильтруем локально
    private var originalList: List<FilterIndustry> = emptyList()

    init {
        loadIndustries()
    }

    fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
        if (originalList.isNotEmpty()) {
            showFiltered(query)
        }
    }

    fun onIndustryClick(industry: FilterIndustry) {
        _state.update { it.copy(selected = industry) }
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            when (val result = industryInteractor.getIndustries()) {
                is Resource.Success -> {
                    originalList = result.data.sortedBy { it.name }
                    showFiltered(_state.value.query)
                }

                is Resource.Error -> {
                    val content = if (result.isNetworkError()) {
                        IndustrySelectionContent.NetworkError
                    } else {
                        IndustrySelectionContent.ServerError
                    }
                    _state.update { it.copy(content = content) }
                }

                Resource.Loading -> {
                    _state.update { it.copy(content = IndustrySelectionContent.Loading) }
                }
            }
        }
    }

    private fun showFiltered(query: String) {
        val filtered = if (query.isBlank()) {
            originalList
        } else {
            originalList.filter { it.name.contains(query.trim(), ignoreCase = true) }
        }
        val content = if (filtered.isEmpty()) {
            IndustrySelectionContent.Empty
        } else {
            IndustrySelectionContent.Content(filtered.toImmutableList())
        }
        _state.update { it.copy(content = content) }
    }

    private fun Resource.Error.isNetworkError(): Boolean =
        code == null || code == NETWORK_ERROR_CODE

    private companion object {
        const val NETWORK_ERROR_CODE = -1
    }
}
