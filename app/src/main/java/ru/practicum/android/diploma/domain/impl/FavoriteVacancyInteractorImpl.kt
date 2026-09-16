package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail

class FavoriteVacancyInteractorImpl(
    private val favoriteVacancyRepository: FavoriteVacancyRepository,
) : FavoriteVacancyInteractor {

    override fun getAllVacancies(): Flow<List<VacancyDetail>> =
        favoriteVacancyRepository.getAllVacancies()

    override suspend fun getVacancyById(vacancyId: String): VacancyDetail? =
        favoriteVacancyRepository.getVacancyById(vacancyId)

    override suspend fun isFavorite(vacancyId: String): Boolean =
        favoriteVacancyRepository.isFavorite(vacancyId)

    override suspend fun addVacancy(vacancy: VacancyDetail) {
        favoriteVacancyRepository.addVacancy(vacancy)
    }

    override suspend fun removeVacancy(vacancyId: String) {
        favoriteVacancyRepository.removeVacancy(vacancyId)
    }

    override suspend fun removeCachedDetail(vacancyId: String) {
        favoriteVacancyRepository.removeVacancy(vacancyId)
    }
}
