package ru.practicum.android.diploma.ui.vacancy

import androidx.annotation.StringRes
import ru.practicum.android.diploma.domain.models.VacancyDetail

sealed interface VacancyContent {

    data object Loading : VacancyContent

    data class Content(
        val vacancy: VacancyDetail,
        val isFavorite: Boolean = false
    ) : VacancyContent

    data class Error(
        @StringRes val messageRes: Int
    ) : VacancyContent

    data object NotFound : VacancyContent
}
