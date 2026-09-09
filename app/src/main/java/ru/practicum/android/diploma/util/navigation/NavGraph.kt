package ru.practicum.android.diploma.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.practicum.android.diploma.ui.countrySelection.CountrySelectionScreen
import ru.practicum.android.diploma.ui.favorites.FavoritesScreen
import ru.practicum.android.diploma.ui.filteringSettings.FilteringSettingsScreen
import ru.practicum.android.diploma.ui.industrySelection.IndustrySelectionScreen
import ru.practicum.android.diploma.ui.mainSearch.MainSearchScreen
import ru.practicum.android.diploma.ui.team.TeamScreen
import ru.practicum.android.diploma.ui.vacancy.VacancyScreen
import ru.practicum.android.diploma.ui.workplaceSelection.WorkplaceSelectionScreen

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
  //  val navController = rememberNavController()

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
