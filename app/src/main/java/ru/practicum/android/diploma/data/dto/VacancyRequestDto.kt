package ru.practicum.android.diploma.data.dto

data class VacancyRequestDto(
    val text: String? = null,
    val area: Int? = null,
    val industry: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean? = null,
    val page: Int? = null,
)
