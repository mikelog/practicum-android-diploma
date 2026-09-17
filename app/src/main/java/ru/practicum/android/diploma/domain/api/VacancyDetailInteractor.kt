package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacancyDetailInteractor {
    suspend fun getDetail(id: String): Resource<VacancyDetail>
}
