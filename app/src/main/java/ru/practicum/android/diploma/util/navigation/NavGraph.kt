package ru.practicum.android.diploma.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.practicum.android.diploma.ui.countryselection.CountrySelectionScreen
import ru.practicum.android.diploma.ui.favorites.FavoritesScreen
import ru.practicum.android.diploma.ui.filteringsettings.FilteringSettingsScreen
import ru.practicum.android.diploma.ui.industryselection.IndustrySelectionScreen
import ru.practicum.android.diploma.ui.mainsearch.MainSearchScreen
import ru.practicum.android.diploma.ui.team.TeamScreen
import ru.practicum.android.diploma.ui.vacancy.VacancyScreen
import ru.practicum.android.diploma.ui.workplaceselection.WorkplaceSelectionScreen

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = ScreenRoute.MainSearch.route) {
            MainSearchScreen(navController)
        }

        composable(route = ScreenRoute.Favorites.route) {
            FavoritesScreen(navController)
        }

        composable(route = ScreenRoute.Team.route) {
            TeamScreen(navController)
        }

        composable(route = ScreenRoute.FilteringSettings.route) {
            FilteringSettingsScreen(navController)
        }

        composable(route = ScreenRoute.WorkplaceSelection.route) {
            WorkplaceSelectionScreen(navController)
        }

        composable(route = ScreenRoute.CountrySelection.route) {
            CountrySelectionScreen(navController)
        }

        composable(route = ScreenRoute.IndustrySelection.route) {
            IndustrySelectionScreen(navController)
        }

        composable(route = ScreenRoute.Vacancy.route) {
            VacancyScreen(navController)
        }

    }

}
