package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.FilterIndustry

interface IndustryRepository {
    suspend fun getIndustries(): Resource<List<FilterIndustry>>
}
