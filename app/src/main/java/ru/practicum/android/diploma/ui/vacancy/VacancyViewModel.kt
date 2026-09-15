package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.api.VacancyDetailInteractor

class VacancyViewModel(
    private val vacancyId: String,
    private val vacancyDetailInteractor: VacancyDetailInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<VacancyContent>(VacancyContent.Loading)
    val state: StateFlow<VacancyContent> = _state.asStateFlow()

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.value = VacancyContent.Loading
            when (val result = vacancyDetailInteractor.getDetail(vacancyId)) {
                is Resource.Success -> {
                    _state.value = VacancyContent.Content(vacancy = result.data)
                }

                is Resource.Error -> {
                    val messageRes = if (result.code == NO_INTERNET_CODE) {
                        R.string.network_error_message
                    } else {
                        R.string.server_error_message
                    }
                    _state.value = VacancyContent.Error(messageRes)
                }

                Resource.Loading -> {
                    _state.value = VacancyContent.Loading
                }
            }
        }
    }

    private companion object {
        const val NO_INTERNET_CODE = -1
    }
}
