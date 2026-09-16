package ru.practicum.android.diploma.data.db.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyDetail
import java.lang.reflect.Type

class FavoriteVacancyConverter(
    private val gson: Gson,
) {
    private val skillsType: Type =
        object : TypeToken<List<String>>() {}.type

    /**
     * Преобразование доменной модели в сущность Room.
     */
    fun toEntity(vacancy: VacancyDetail): FavoriteVacancyEntity {
        return FavoriteVacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            salary = vacancy.salary?.let { salary ->
                gson.toJson(salary)
            },
            employerJson = gson.toJson(vacancy.employer),
            description = vacancy.description,
            addressJson = vacancy.address?.let { address ->
                gson.toJson(address)
            },
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contactsJson = vacancy.contacts?.let { contacts ->
                gson.toJson(contacts)
            },
            skills = gson.toJson(vacancy.skills),
            url = vacancy.url,
            areaJson = gson.toJson(vacancy.area),
            industryJson = gson.toJson(vacancy.industry),
        )
    }

    /**
     * Преобразование сущности Room в доменную модель.
     */
    fun toDomain(entity: FavoriteVacancyEntity): VacancyDetail {
        return VacancyDetail(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            salary = entity.salary?.let { salaryJson ->
                gson.fromJson(salaryJson, Salary::class.java)
            },
            address = entity.addressJson?.let { addressJson ->
                gson.fromJson(addressJson, Address::class.java)
            },
            experience = entity.experience,
            schedule = entity.schedule,
            employment = entity.employment,
            contacts = entity.contactsJson?.let { contactsJson ->
                gson.fromJson(contactsJson, Contacts::class.java)
            },
            employer = gson.fromJson(
                entity.employerJson,
                Employer::class.java,
            ),
            area = gson.fromJson(
                entity.areaJson,
                FilterArea::class.java,
            ),
            skills = entity.skills?.let { skillsJson ->
                gson.fromJson<List<String>>(
                    skillsJson,
                    skillsType,
                )
            }.orEmpty(),
            url = entity.url,
            industry = gson.fromJson(
                entity.industryJson,
                FilterIndustry::class.java,
            ),
        )
    }
}
