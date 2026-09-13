package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.models.VacancyResponse

interface SearchVacancyRepository {
    suspend fun searchVacancy(
        text: String?,
        areaId: Int?,
        industryId: Int?,
        salary: Int?,
        onlyWithSalary: Boolean?,
        page: Int?
    ): Resource<VacancyResponse>
}
