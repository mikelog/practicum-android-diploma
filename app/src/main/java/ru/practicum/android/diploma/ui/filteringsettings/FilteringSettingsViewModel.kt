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

    private val defaultParameters = FilterParameters()

    private val initialParameters: FilterParameters =
        filterSettingsInteractor.get()

    private val _uiState = MutableStateFlow(
        FilteringSettingsUiState(
            parameters = initialParameters,
            savedParameters = initialParameters,
        )
    )

    val uiState: StateFlow<FilteringSettingsUiState> =
        _uiState.asStateFlow()

    fun onSalaryChanged(value: String) {
        val salary = value
            .takeIf { it.isNotBlank() }
            ?.toIntOrNull()

        updateParameters { currentParameters ->
            currentParameters.copy(salary = salary)
        }
    }

    fun onIndustryChanged(industryId: Int?) {
        updateParameters { currentParameters ->
            currentParameters.copy(industryId = industryId)
        }
    }

    fun onOnlyWithSalaryChanged(value: Boolean) {
        updateParameters { currentParameters ->
            currentParameters.copy(onlyWithSalary = value)
        }
    }

    fun onApplyClicked() {
        val currentParameters = _uiState.value.parameters

        filterSettingsInteractor.save(currentParameters)

        _uiState.update { currentState ->
            currentState.copy(
                savedParameters = currentParameters,
            )
        }
    }

    fun onResetClicked() {
        filterSettingsInteractor.clear()

        _uiState.value = FilteringSettingsUiState(
            parameters = defaultParameters,
            savedParameters = defaultParameters,
        )
    }

    private fun updateParameters(
        transform: (FilterParameters) -> FilterParameters,
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                parameters = transform(currentState.parameters),
            )
        }
    }
}
