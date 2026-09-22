package ru.practicum.android.diploma.ui.industryselection

import kotlinx.collections.immutable.ImmutableList
import ru.practicum.android.diploma.domain.models.FilterIndustry

sealed interface IndustrySelectionContent {
    data object Loading : IndustrySelectionContent
    data class Content(val industries: ImmutableList<FilterIndustry>) : IndustrySelectionContent
    data object Empty : IndustrySelectionContent
    data object NetworkError : IndustrySelectionContent
    data object ServerError : IndustrySelectionContent
}