package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.VacancyDetailRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailRepository
import ru.practicum.android.diploma.domain.impl.VacancyDetailInteractorImpl
import ru.practicum.android.diploma.ui.vacancy.VacancyViewModel

val vacancyDetailModule = module {

    single<VacancyDetailRepository> {
        VacancyDetailRepositoryImpl(
            vacancyApiService = get(),
            connectivityChecker = get(),
        )
    }

    factory<VacancyDetailInteractor> {
        VacancyDetailInteractorImpl(
            vacancyDetailRepository = get(),
        )
    }

    viewModel { (vacancyId: String) ->
        VacancyViewModel(
            vacancyId = vacancyId,
            vacancyDetailInteractor = get(),
        )
    }
}
