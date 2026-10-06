package ru.practicum.android.diploma.domain.models

data class FilterParameters(
    val industry: FilterIndustry? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
) {
    val isEmpty: Boolean
        get() = this == FilterParameters()
}
