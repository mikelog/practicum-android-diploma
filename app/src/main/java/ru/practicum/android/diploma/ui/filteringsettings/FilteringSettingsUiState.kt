package ru.practicum.android.diploma.ui.filteringsettings

import ru.practicum.android.diploma.domain.models.FilterParameters

data class FilteringSettingsUiState(
    val parameters: FilterParameters = FilterParameters()
)
