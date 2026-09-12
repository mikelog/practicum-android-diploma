package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.PhoneDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.VacancyCardDto
import ru.practicum.android.diploma.data.dto.VacancyCardSalaryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyCard
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.models.VacancyResponse


fun VacancyResponseDto.toDomain(): VacancyResponse = VacancyResponse(
    found = found,
    pages = pages,
    page = page,
    items = items.map { it.toDomain() }
)

fun VacancyCardDto.toDomain(): VacancyCard = VacancyCard(
    id = id,
    name = name,
    company = company,
    city = city,
    salary = salary?.toDomain(),
    logo = logo
)

fun VacancyDetailDto.toDomain(): VacancyDetail = VacancyDetail(
    id = id,
    name = name,
    description = description,
    salary = salary?.toDomain(),
    address = address?.toDomain(),
    experience = experience?.name,
    schedule = schedule?.name,
    employment = employment?.name,
    contacts = contacts?.toDomain(),
    employer = employer.toDomain(),
    area = area.toDomain(),
    skills = skills.orEmpty(),
    url = url,
    industry = industry.toDomain()
)

fun SalaryDto.toDomain(): Salary = Salary(
    from = from,
    to = to,
    currency = currency
)

fun VacancyCardSalaryDto.toDomain(): Salary = Salary(
    from = from,
    to = to,
    currency = currency
)

fun AddressDto.toDomain(): Address = Address(
    id = id,
    city = city,
    street = street,
    building = building,
    raw = raw
)

fun ContactsDto.toDomain(): Contacts = Contacts(
    name = name,
    email = email,
    phones = phones.map { it.toDomain() }
)

fun PhoneDto.toDomain(): Phone = Phone(
    comment = comment,
    formatted = formatted
)

fun EmployerDto.toDomain(): Employer = Employer(
    name = name,
    logo = logo
)

fun FilterAreaDto.toDomain(): FilterArea = FilterArea(
    id = id,
    name = name,
    parentId = parentId,
    areas = areas.orEmpty().map { it.toDomain() }
)

fun FilterIndustryDto.toDomain(): FilterIndustry = FilterIndustry(
    id = id,
    name = name
)
