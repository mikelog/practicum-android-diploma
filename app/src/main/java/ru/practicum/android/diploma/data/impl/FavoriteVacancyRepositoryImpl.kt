package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.converter.FavoriteVacancyConverter
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail

class FavoriteVacancyRepositoryImpl(
    private val favoriteVacancyDao: FavoriteVacancyDao,
    private val converter: FavoriteVacancyConverter,
) : FavoriteVacancyRepository {

    override fun getAllVacancies(): Flow<List<VacancyDetail>> =
        favoriteVacancyDao.getAllVacancies().map { entities ->
            entities.map { converter.toDomain(it) }
        }

    override suspend fun getVacancyById(vacancyId: String): VacancyDetail? =
        favoriteVacancyDao.getVacancyById(vacancyId)?.let { converter.toDomain(it) }

    override suspend fun isFavorite(vacancyId: String): Boolean =
        favoriteVacancyDao.getVacancyById(vacancyId) != null

    override suspend fun addVacancy(vacancy: VacancyDetail) {
        favoriteVacancyDao.insertVacancy(converter.toEntity(vacancy))
    }

    override suspend fun removeVacancy(vacancyId: String) {
        favoriteVacancyDao.deleteVacancyById(vacancyId)
    }
}
