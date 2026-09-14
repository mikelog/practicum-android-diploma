package ru.practicum.android.diploma.domain.models

data class VacancySearchParams(
    val text: String? = null,
    val areaId: Int? = null,
    val industryId: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean? = null,
    val page: Int? = null
)
