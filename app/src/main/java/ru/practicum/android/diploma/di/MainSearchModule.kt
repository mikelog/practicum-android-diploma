package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.SearchVacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.domain.impl.SearchVacancyInteractorImpl
import ru.practicum.android.diploma.ui.mainsearch.MainSearchViewModel

val mainSearchModule = module {

    single<SearchVacancyRepository> {
        SearchVacancyRepositoryImpl(
            vacancyApiService = get(),
            connectivityChecker = get(),
        )
    }

    factory<SearchVacancyInteractor> {
        SearchVacancyInteractorImpl(
            searchVacancyRepository = get(),
        )
    }

    viewModel {
        MainSearchViewModel(
            searchVacancyInteractor = get(),
            filterSettingsInteractor = get(),
        )
    }
}
