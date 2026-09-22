package ru.practicum.android.diploma.ui.favorites

import kotlinx.collections.immutable.ImmutableList
import ru.practicum.android.diploma.domain.models.VacancyCard

sealed interface FavoritesContent {
    data object Loading : FavoritesContent
    data class Content(val vacancies: ImmutableList<VacancyCard>) : FavoritesContent
    data object Empty : FavoritesContent
    data object Error : FavoritesContent
}
