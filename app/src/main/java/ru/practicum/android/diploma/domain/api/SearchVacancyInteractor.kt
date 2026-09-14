package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.models.VacancyResponse
import ru.practicum.android.diploma.domain.models.VacancySearchParams

interface SearchVacancyInteractor {
    suspend fun searchVacancy(
        params: VacancySearchParams,
    ): Resource<VacancyResponse>
}
