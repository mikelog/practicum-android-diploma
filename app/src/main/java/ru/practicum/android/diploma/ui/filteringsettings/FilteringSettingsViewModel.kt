package ru.practicum.android.diploma.ui.filteringsettings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.FilterParameters

// Любое изменение настроек сразу сохраняется (ТЗ: «сохраняются автоматически сразу после изменения»)
class FilteringSettingsViewModel(
    private val filterSettingsInteractor: FilterSettingsInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FilteringSettingsUiState(
            parameters = filterSettingsInteractor.get()
        )
    )

    val uiState: StateFlow<FilteringSettingsUiState> =
        _uiState.asStateFlow()

    fun onSalaryChanged(text: String) {
        updateParameters { it.copy(salary = text.toIntOrNull()) }
    }

    fun onOnlyWithSalaryToggled() {
        updateParameters { it.copy(onlyWithSalary = !it.onlyWithSalary) }
    }

    fun onIndustrySelected(industry: FilterIndustry) {
        updateParameters { it.copy(industry = industry) }
    }

    fun onIndustryCleared() {
        updateParameters { it.copy(industry = null) }
    }

    fun onResetClicked() {
        filterSettingsInteractor.clear()
        _uiState.value = FilteringSettingsUiState()
    }

    private fun updateParameters(transform: (FilterParameters) -> FilterParameters) {
        val current = _uiState.value.parameters
        val updated = transform(current)
        if (updated == current) return
        filterSettingsInteractor.save(updated)
        _uiState.value = _uiState.value.copy(parameters = updated)
    }
}
