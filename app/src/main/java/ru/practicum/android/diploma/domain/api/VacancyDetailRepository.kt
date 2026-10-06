package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacancyDetailRepository {
    suspend fun getDetail(id: String): Resource<VacancyDetail>
}
