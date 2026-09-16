package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.models.FilterIndustry

class IndustryInteractorImpl(
    private val industryRepository: IndustryRepository,
) : IndustryInteractor {
    override suspend fun getIndustries(): Resource<List<FilterIndustry>> {
        return industryRepository.getIndustries()
    }
}
