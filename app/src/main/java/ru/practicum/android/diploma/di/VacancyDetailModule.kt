package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.VacancyDetailRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailRepository
import ru.practicum.android.diploma.domain.impl.VacancyDetailInteractorImpl

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
}
