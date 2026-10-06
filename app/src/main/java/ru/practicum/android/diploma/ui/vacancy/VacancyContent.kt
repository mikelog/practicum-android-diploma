package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.domain.models.VacancyDetail

sealed interface VacancyContent {

    data object Loading : VacancyContent

    data class Content(
        val vacancy: VacancyDetail,
        val isFavorite: Boolean = false
    ) : VacancyContent

    data object NetworkError : VacancyContent

    data object ServerError : VacancyContent

    data object NotFound : VacancyContent
}
