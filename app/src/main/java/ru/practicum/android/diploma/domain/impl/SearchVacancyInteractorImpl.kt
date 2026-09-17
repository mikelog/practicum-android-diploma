package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyResponse
import ru.practicum.android.diploma.domain.models.VacancySearchParams

class SearchVacancyInteractorImpl(
    private val searchVacancyRepository: SearchVacancyRepository,
) : SearchVacancyInteractor {
    override suspend fun searchVacancy(params: VacancySearchParams): Resource<VacancyResponse> {
        return searchVacancyRepository.searchVacancy(params)
    }
}
