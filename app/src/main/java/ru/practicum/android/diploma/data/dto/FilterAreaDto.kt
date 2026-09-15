package ru.practicum.android.diploma.data.dto

data class FilterAreaDto(
    val id: String,
    val name: String,
    val parentId: String?,
    val areas: List<FilterAreaDto>?,
)
