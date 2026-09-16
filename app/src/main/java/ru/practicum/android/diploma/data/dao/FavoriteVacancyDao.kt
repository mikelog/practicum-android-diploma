package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {

    /**
     * Возвращает все избранные вакансии.
     *
     * Flow автоматически отправит новый список,
     * когда содержимое таблицы изменится.
     */
    @Query("SELECT * FROM favorite_vacancy")
    fun getAllVacancies(): Flow<List<FavoriteVacancyEntity>>

    /**
     * Возвращает одну вакансию по id.
     *
     * Если вакансия отсутствует, возвращает null.
     */
    @Query(
        """
        SELECT * FROM favorite_vacancy
        WHERE id = :vacancyId
        LIMIT 1
        """
    )
    suspend fun getVacancyById(
        vacancyId: String,
    ): FavoriteVacancyEntity?

    /**
     * Добавляет вакансию в избранное.
     *
     * Если запись с таким id уже существует,
     * она будет заменена новой записью.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(
        vacancy: FavoriteVacancyEntity,
    )

    /**
     * Удаляет вакансию по id.
     *
     * Возвращает количество удалённых записей:
     * 0 — запись не найдена;
     * 1 — запись удалена.
     */
    @Query(
        """
        DELETE FROM favorite_vacancy
        WHERE id = :vacancyId
        """
    )
    suspend fun deleteVacancyById(
        vacancyId: String,
    ): Int
}
