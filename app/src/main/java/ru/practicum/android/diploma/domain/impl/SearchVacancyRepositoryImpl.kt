package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.data.mapper.toDomain
import ru.practicum.android.diploma.data.network.ConnectivityChecker
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.data.network.VacancyApiService
import ru.practicum.android.diploma.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyResponse

class SearchVacancyRepositoryImpl(
    private val vacancyApiService: VacancyApiService,
    private val connectivityChecker: ConnectivityChecker,
) : SearchVacancyRepository {

    override suspend fun searchVacancy(
        text: String?,
        areaId: Int?,
        industryId: Int?,
        salary: Int?,
        onlyWithSalary: Boolean?,
        page: Int?,
    ): Resource<VacancyResponse> {
        val result: Resource<VacancyResponseDto> =
            NetworkClient.doRequest(connectivityChecker) {
                vacancyApiService.getVacancies(
                    text = text,
                    area = areaId,
                    industry = industryId,
                    salary = salary,
                    onlyWithSalary = onlyWithSalary,
                    page = page,
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
