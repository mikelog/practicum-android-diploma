package ru.practicum.android.diploma.data.impl

import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.mapper.toDomain
import ru.practicum.android.diploma.data.network.ConnectivityChecker
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.data.network.VacancyApiService
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.models.FilterIndustry

class IndustryRepositoryImpl(
    private val vacancyApiService: VacancyApiService,
    private val connectivityChecker: ConnectivityChecker,
) : IndustryRepository {

    override suspend fun getIndustries(): Resource<List<FilterIndustry>> {
        val result: Resource<List<FilterIndustryDto>> =
            NetworkClient.doRequest(connectivityChecker) {
                vacancyApiService.getIndustries()
            }

        return when (result) {
            is Resource.Success -> {
                Resource.Success(
                    data = result.data.map { it.toDomain() },
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
