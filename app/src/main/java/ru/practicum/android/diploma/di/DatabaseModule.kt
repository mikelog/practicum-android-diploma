package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase

val databaseModule: Module = module {

    single<AppDatabase> { AppDatabase.create(androidContext()) }

    // DAO биндят фиче-модули: single { get<AppDatabase>().favoriteVacancyDao() }
}
