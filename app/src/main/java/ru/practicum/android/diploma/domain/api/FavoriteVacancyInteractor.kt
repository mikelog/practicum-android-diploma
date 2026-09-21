package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface FavoriteVacancyInteractor {

    fun getAllVacancies(): Flow<List<VacancyDetail>>

    suspend fun getVacancyById(vacancyId: String): VacancyDetail?

    suspend fun isFavorite(vacancyId: String): Boolean

    suspend fun addVacancy(vacancy: VacancyDetail)

    suspend fun removeVacancy(vacancyId: String)

    /**
     * Удаляет закешированные детали вакансии из избранного при получении 404 с сервера.
     */
    suspend fun removeCachedDetail(vacancyId: String)
}
