package ru.practicum.android.diploma.data.impl

import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.mapper.toDomain
import ru.practicum.android.diploma.data.network.ConnectivityChecker
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.data.network.VacancyApiService
import ru.practicum.android.diploma.domain.api.VacancyDetailRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDetailRepositoryImpl(
    private val vacancyApiService: VacancyApiService,
    private val connectivityChecker: ConnectivityChecker,
) : VacancyDetailRepository {

    override suspend fun getDetail(id: String): Resource<VacancyDetail> {
        val result: Resource<VacancyDetailDto> =
            NetworkClient.doRequest(connectivityChecker) {
                vacancyApiService.getVacancyDetail(id)
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
