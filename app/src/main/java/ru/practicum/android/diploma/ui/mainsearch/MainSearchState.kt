package ru.practicum.android.diploma.ui.mainsearch

data class MainSearchState(
    val query: String = "",
    val content: MainSearchContent = MainSearchContent.Idle
)
