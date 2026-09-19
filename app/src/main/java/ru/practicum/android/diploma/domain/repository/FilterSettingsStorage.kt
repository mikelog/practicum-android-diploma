package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.FilterParameters

interface FilterSettingsStorage {
    fun save(parameters: FilterParameters)
    fun get(): FilterParameters
    fun clear()
}
