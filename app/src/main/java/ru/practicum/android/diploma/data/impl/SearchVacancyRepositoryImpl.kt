package ru.practicum.android.diploma.data.impl

import ru.practicum.android.diploma.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.data.mapper.toDomain
import ru.practicum.android.diploma.data.mapper.toDto
import ru.practicum.android.diploma.data.network.ConnectivityChecker
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.data.network.VacancyApiService
import ru.practicum.android.diploma.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyResponse
import ru.practicum.android.diploma.domain.models.VacancySearchParams

class SearchVacancyRepositoryImpl(
    private val vacancyApiService: VacancyApiService,
    private val connectivityChecker: ConnectivityChecker,
) : SearchVacancyRepository {

    override suspend fun searchVacancy(
        params: VacancySearchParams,
    ): Resource<VacancyResponse> {
        val request = params.toDto()

        val result: Resource<VacancyResponseDto> =
            NetworkClient.doRequest(connectivityChecker) {
                vacancyApiService.getVacancies(
                    area = request.area,
                    industry = request.industry,
                    text = request.text,
                    salary = request.salary,
                    page = request.page,
                    onlyWithSalary = request.onlyWithSalary,
                )
            }

        return when (result) {
            is Resource.Success -> {
                Resource.Success(
                    data = result.data.toDomain(),
                )
            }

            is Resource.Error -> {
                Resource.Error(
                    message = result.message,
                    code = result.code,
                )
            }

            Resource.Loading -> {
                Resource.Loading
            }
        }
    }
}
