package ru.practicum.android.diploma.domain.models

data class FilterParameters(
    val industryId: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)
