package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.FilterParameters

interface FilterSettingsInteractor {
    fun save(parameters: FilterParameters)
    fun get(): FilterParameters
    fun clear()
}
