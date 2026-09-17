package ru.practicum.android.diploma.util.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
        composable(
            route = ScreenRoute.MainSearch.route,
            enterTransition = { tabFadeIn() },
            exitTransition = { tabFadeOut() },
            popEnterTransition = { tabFadeIn() },
            popExitTransition = { tabFadeOut() }
        ) {
            MainSearchScreen(navController)
        }

        composable(
            route = ScreenRoute.Favorites.route,
            enterTransition = { tabFadeIn() },
            exitTransition = { tabFadeOut() },
            popEnterTransition = { tabFadeIn() },
            popExitTransition = { tabFadeOut() }
        ) {
            FavoritesScreen(navController)
        }

        composable(
            route = ScreenRoute.Team.route,
            enterTransition = { tabFadeIn() },
            exitTransition = { tabFadeOut() },
            popEnterTransition = { tabFadeIn() },
            popExitTransition = { tabFadeOut() }
        ) {
            TeamScreen(navController)
        }

        composable(
            route = ScreenRoute.FilteringSettings.route,
            enterTransition = { filterSlideInForward() },
            exitTransition = { filterSlideOutForward() },
            popEnterTransition = { filterPopSlideInBack() },
            popExitTransition = { filterPopSlideOutBack() }
        ) {
            FilteringSettingsScreen(navController)
        }

        composable(
            route = ScreenRoute.WorkplaceSelection.route,
            enterTransition = { filterSlideInForward() },
            exitTransition = { filterSlideOutForward() },
            popEnterTransition = { filterPopSlideInBack() },
            popExitTransition = { filterPopSlideOutBack() }
        ) {
            WorkplaceSelectionScreen(navController)
        }

        composable(
            route = ScreenRoute.CountrySelection.route,
            enterTransition = { filterSlideInForward() },
            exitTransition = { filterSlideOutForward() },
            popEnterTransition = { filterPopSlideInBack() },
            popExitTransition = { filterPopSlideOutBack() }
        ) {
            CountrySelectionScreen(navController)
        }

        composable(
            route = ScreenRoute.IndustrySelection.route,
            enterTransition = { filterSlideInForward() },
            exitTransition = { filterSlideOutForward() },
            popEnterTransition = { filterPopSlideInBack() },
            popExitTransition = { filterPopSlideOutBack() }
        ) {
            IndustrySelectionScreen(navController)
        }

        composable(
            route = ScreenRoute.Vacancy.route,
            arguments = listOf(navArgument("vacancyId") { type = NavType.StringType }),
            enterTransition = { filterSlideInForward() },
            exitTransition = { filterSlideOutForward() },
            popEnterTransition = { filterPopSlideInBack() },
            popExitTransition = { filterPopSlideOutBack() }
        ) { backStackEntry ->
            val vacancyId = backStackEntry.arguments?.getString("vacancyId").orEmpty()
            VacancyScreen(navController, vacancyId)
        }

    }

}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.filterSlideInForward(): EnterTransition =
    slideInHorizontally(animationSpec = tween(TRANSITION_DURATION)) { it } + fadeIn()

private fun AnimatedContentTransitionScope<NavBackStackEntry>.filterSlideOutForward(): ExitTransition =
    slideOutHorizontally(animationSpec = tween(TRANSITION_DURATION)) { -it / 4 } + fadeOut()

private fun AnimatedContentTransitionScope<NavBackStackEntry>.filterPopSlideInBack(): EnterTransition =
    slideInHorizontally(animationSpec = tween(TRANSITION_DURATION)) { -it / 4 } + fadeIn()

private fun AnimatedContentTransitionScope<NavBackStackEntry>.filterPopSlideOutBack(): ExitTransition =
    slideOutHorizontally(animationSpec = tween(TRANSITION_DURATION)) { it } + fadeOut()

private fun AnimatedContentTransitionScope<NavBackStackEntry>.tabFadeIn(): EnterTransition =
    fadeIn(animationSpec = tween(TAB_TRANSITION_DURATION))

private fun AnimatedContentTransitionScope<NavBackStackEntry>.tabFadeOut(): ExitTransition =
    fadeOut(animationSpec = tween(TAB_TRANSITION_DURATION))

private const val TRANSITION_DURATION = 300
private const val TAB_TRANSITION_DURATION = 200
