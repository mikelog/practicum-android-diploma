package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.api.FilterSettingsStorage
import ru.practicum.android.diploma.domain.models.FilterParameters

class FilterSettingsInteractorImpl(
    private val filterSettingsStorage: FilterSettingsStorage
) : FilterSettingsInteractor {

    override fun save(parameters: FilterParameters) {
        filterSettingsStorage.save(parameters)
    }

    override fun get(): FilterParameters {
        return filterSettingsStorage.get()
    }

    override fun clear() {
        filterSettingsStorage.clear()
    }
}
