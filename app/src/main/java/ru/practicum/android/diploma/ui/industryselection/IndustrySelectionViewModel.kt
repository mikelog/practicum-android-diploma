package ru.practicum.android.diploma.ui.industryselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.api.IndustryInteractor

class IndustrySelectionViewModel(
    private val industryInteractor: IndustryInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow<IndustrySelectionContent>(
        IndustrySelectionContent.Loading
    )

    val state: StateFlow<IndustrySelectionContent> = _state.asStateFlow()

    init {
        loadIndustries()
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            when (val result = industryInteractor.getIndustries()) {
                is Resource.Success -> {
                    _state.value = if (result.data.isEmpty()) {
                        IndustrySelectionContent.Empty
                    } else {
                        IndustrySelectionContent.Content(
                            industries = result.data.toImmutableList()
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = if (result.isNetworkError()) {
                        IndustrySelectionContent.NetworkError
                    } else {
                        IndustrySelectionContent.ServerError
                    }
                }

                Resource.Loading -> {
                    _state.value = IndustrySelectionContent.Loading
                }
            }
        }
    }

    private fun Resource.Error.isNetworkError(): Boolean =
        code == null || code == NETWORK_ERROR_CODE

    private companion object {
        const val NETWORK_ERROR_CODE = -1
    }
}