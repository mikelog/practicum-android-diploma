package ru.practicum.android.diploma.ui.filteringsettings

import ru.practicum.android.diploma.domain.models.FilterParameters

data class FilteringSettingsUiState(
    val parameters: FilterParameters = FilterParameters(),
    val savedParameters: FilterParameters = FilterParameters(),
) {
    val isApplyButtonVisible: Boolean
        get() = parameters != savedParameters

    val isResetButtonVisible: Boolean
        get() = parameters != FilterParameters()
}
