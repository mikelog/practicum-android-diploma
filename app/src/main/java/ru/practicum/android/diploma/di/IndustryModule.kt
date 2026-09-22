package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.IndustryRepositoryImpl
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.impl.IndustryInteractorImpl
import ru.practicum.android.diploma.ui.industryselection.IndustrySelectionViewModel

val industryModule = module {

    single<IndustryRepository> {
        IndustryRepositoryImpl(
            vacancyApiService = get(),
            connectivityChecker = get(),
        )
    }

    factory<IndustryInteractor> {
        IndustryInteractorImpl(
            industryRepository = get(),
        )
    }

    viewModel {
        IndustrySelectionViewModel(
            industryInteractor = get(),
        )
    }
}
