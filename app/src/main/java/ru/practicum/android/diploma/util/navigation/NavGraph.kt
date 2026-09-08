package ru.practicum.android.diploma.util.navigation

import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.ui.main.MainScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.ui.favorites.FavoritesScreen
import ru.practicum.android.diploma.ui.team.TeamScreen


@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Main.route
    ) {

        composable(route = ScreenRoute.Main.route) {
            MainScreen(navController)
        }

        composable(route = ScreenRoute.Favorites.route) {
            FavoritesScreen(navController)
        }

        composable(route = ScreenRoute.Team.route) {
            TeamScreen(navController)
        }

    }


}
