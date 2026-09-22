package ru.practicum.android.diploma.ui.filteringsettings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.practicum.android.diploma.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.models.FilterParameters

class FilteringSettingsViewModel(
    private val filterSettingsInteractor: FilterSettingsInteractor,
) : ViewModel() {

    private val initialParameters: FilterParameters =
        filterSettingsInteractor.get()

    private val _uiState = MutableStateFlow(
        FilteringSettingsUiState(
            parameters = initialParameters
        )
    )

    val uiState: StateFlow<FilteringSettingsUiState> =
        _uiState.asStateFlow()

    fun onApplyClicked(parameters: FilterParameters) {
        filterSettingsInteractor.save(parameters)

        _uiState.update { currentState ->
            currentState.copy(
                parameters = parameters
            )
        }
    }

    fun onResetClicked() {
        val defaultParameters = FilterParameters()

        filterSettingsInteractor.clear()

        _uiState.update { currentState ->
            currentState.copy(
                parameters = defaultParameters
            )
        }
    }
}
