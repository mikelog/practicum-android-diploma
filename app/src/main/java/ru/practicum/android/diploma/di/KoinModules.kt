package ru.practicum.android.diploma.di

import org.koin.core.module.Module

/**
 * Соглашение: свой фиче-модуль заводим отдельным файлом в пакете `di`
 * и дописываем в конец этого списка. Чужие модули не редактируем.
 */
val koinModules: List<Module> = listOf(
    appModule,
    networkModule,
    databaseModule,
)
