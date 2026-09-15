package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.practicum.android.diploma.data.network.ConnectivityChecker
import ru.practicum.android.diploma.data.network.ConnectivityCheckerImpl
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.VacancyApiService

val networkModule: Module = module {

    single<ConnectivityChecker> { ConnectivityCheckerImpl(androidContext()) }

    single { NetworkClient }

    single<VacancyApiService> { NetworkClient.vacancyApiService }
}
