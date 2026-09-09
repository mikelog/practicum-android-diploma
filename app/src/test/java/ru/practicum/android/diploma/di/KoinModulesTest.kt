package ru.practicum.android.diploma.di

import android.content.Context
import io.mockk.mockk
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.test.check.checkKoinModules

class KoinModulesTest {

    // Поднимает весь граф Koin и создаёт каждую зависимость.
    // Падает на CI, если в каком-то модуле из koinModules есть get(), который нечем разрешить.
    @Test
    fun `koin graph resolves`() {
        checkKoinModules(
            modules = koinModules,
            appDeclaration = { androidContext(mockk<Context>(relaxed = true)) },
        )
    }
}
