package ru.practicum.android.diploma.ui.favorites

import ru.practicum.android.diploma.domain.models.VacancyCard

sealed interface FavoritesContent {
    data object Loading : FavoritesContent
    data class Content(val vacancies: List<VacancyCard>) : FavoritesContent
    data object Empty : FavoritesContent
    data object Error : FavoritesContent
}
