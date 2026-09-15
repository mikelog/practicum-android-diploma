package ru.practicum.android.diploma.data.dto

data class VacancyResponseDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyCardDto>,
)

data class VacancyCardDto(
    val id: String,
    val name: String,
    val company: String?,
    val city: String?,
    val salary: VacancyCardSalaryDto?,
    val logo: String?,
)

data class VacancyCardSalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String?,
)
