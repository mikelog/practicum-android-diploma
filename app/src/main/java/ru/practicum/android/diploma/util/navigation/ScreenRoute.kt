package ru.practicum.android.diploma.util.navigation

sealed class ScreenRoute(val route: String) {
    object MainSearch : ScreenRoute("main-search")
    object Favorites : ScreenRoute("favorites")
    object Team : ScreenRoute("team")
    object FilteringSettings : ScreenRoute("filtering-settings")
    object WorkplaceSelection : ScreenRoute("workplace-selection")
    object CountrySelection : ScreenRoute("country-selection")
    object IndustrySelection : ScreenRoute("industry-selection")
    object Vacancy : ScreenRoute("vacancy")
}
