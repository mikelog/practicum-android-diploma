package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {

    @Query("SELECT * FROM favorite_vacancy")
    fun getAllVacancies(): Flow<List<FavoriteVacancyEntity>>

    @Query("SELECT * FROM favorite_vacancy WHERE id = :vacancyId LIMIT 1")
    suspend fun getVacancyById(vacancyId: String): FavoriteVacancyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: FavoriteVacancyEntity): Long

    @Query("DELETE FROM favorite_vacancy WHERE id = :vacancyId")
    suspend fun deleteVacancyById(vacancyId: String): Int
}
