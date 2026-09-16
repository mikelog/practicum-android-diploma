package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converter.FavoriteVacancyConverter
import ru.practicum.android.diploma.data.impl.FavoriteVacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.domain.impl.FavoriteVacancyInteractorImpl

val favoritesModule = module {

    single { get<AppDatabase>().favoriteVacancyDao() }

    single { Gson() }

    single { FavoriteVacancyConverter(gson = get()) }

    single<FavoriteVacancyRepository> {
        FavoriteVacancyRepositoryImpl(
            favoriteVacancyDao = get(),
            converter = get(),
        )
    }

    factory<FavoriteVacancyInteractor> {
        FavoriteVacancyInteractorImpl(
            favoriteVacancyRepository = get(),
        )
    }

    // viewModel {...} допишет Presentation (A)
}
