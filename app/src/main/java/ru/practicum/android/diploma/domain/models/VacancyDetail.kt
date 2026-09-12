package ru.practicum.android.diploma.domain.models


data class VacancyDetail(
    val id: String,
    val name: String,
    val description: String,
    val salary: Salary?,
    val address: Address?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val contacts: Contacts?,
    val employer: Employer,
    val area: FilterArea,
    val skills: List<String>,
    val url: String,
    val industry: FilterIndustry
)

data class Address(
    val id: String,
    val city: String,
    val street: String,
    val building: String,
    val raw: String
)

data class Contacts(
    val name: String,
    val email: String,
    val phones: List<Phone>
)

data class Phone(
    val comment: String?,
    val formatted: String
)

data class Employer(
    val name: String,
    val logo: String
)
