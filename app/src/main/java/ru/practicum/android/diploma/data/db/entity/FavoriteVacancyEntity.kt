package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancy")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val salary: String?,
    val employerName: String?,
    val employerLogo: String?,
    val city: String?,
    val description: String,
    val addressJson: String?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val contactsJson: String?,
    val skills: String?,
    val url: String,
)
