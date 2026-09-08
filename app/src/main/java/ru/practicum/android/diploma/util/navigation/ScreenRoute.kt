package ru.practicum.android.diploma.util.navigation

sealed class ScreenRoute (val route: String) {
    object Main : ScreenRoute("main")
    object Favorites : ScreenRoute("favorites")
    object Team : ScreenRoute("team")
}
