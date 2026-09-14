package ru.practicum.android.diploma.ui.mainsearch

import ru.practicum.android.diploma.domain.models.VacancyCard

sealed interface MainSearchContent {

    data object Idle : MainSearchContent

    data object Loading : MainSearchContent

    data class Content(
        val vacancies: List<VacancyCard>,
        val found: Int
    ) : MainSearchContent

    data object Empty : MainSearchContent

    data object NetworkError : MainSearchContent

    data object ServerError : MainSearchContent
}
