package ru.practicum.android.diploma.ui.mainsearch

import kotlinx.collections.immutable.ImmutableList
import ru.practicum.android.diploma.domain.models.VacancyCard

sealed interface MainSearchContent {

    data object Idle : MainSearchContent

    data object Loading : MainSearchContent

    data class Content(
        val vacancies: ImmutableList<VacancyCard>,
        val found: Int
    ) : MainSearchContent

    data object Empty : MainSearchContent

    data object NetworkError : MainSearchContent

    data object ServerError : MainSearchContent
}
