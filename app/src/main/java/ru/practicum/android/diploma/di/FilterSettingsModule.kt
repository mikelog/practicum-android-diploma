package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.FilterSettingsStorageImpl
import ru.practicum.android.diploma.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.api.FilterSettingsStorage
import ru.practicum.android.diploma.domain.impl.FilterSettingsInteractorImpl

val filterSettingsModule = module {

    single<FilterSettingsStorage> {
        FilterSettingsStorageImpl(
            context = androidContext(),
        )
    }

    factory<FilterSettingsInteractor> {
        FilterSettingsInteractorImpl(
            filterSettingsStorage = get(),
        )
    }
}
