package ru.practicum.android.diploma.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Database(
    entities = [FavoriteVacancyEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteVacancyDao(): FavoriteVacancyDao

    companion object {
        private const val DATABASE_NAME = "vacancy_db.db"
        fun create(context: Context): AppDatabase =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME,
            ).build()
    }
}
