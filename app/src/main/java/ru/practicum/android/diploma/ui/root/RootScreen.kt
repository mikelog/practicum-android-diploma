package ru.practicum.android.diploma.ui.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.navigation.NavGraph
import ru.practicum.android.diploma.util.navigation.ScreenRoute

@Composable
fun RootScreen() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = setOf(
        ScreenRoute.MainSearch.route,
        ScreenRoute.Favorites.route,
        ScreenRoute.Team.route
    )

    val showBottomBar = currentRoute != null && currentRoute in bottomBarRoutes

    val navigationItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = Color.Transparent,
        unselectedIconColor = MaterialTheme.colorScheme.outline,
        unselectedTextColor = MaterialTheme.colorScheme.outline
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomBar) {
                Column {
                    HorizontalDivider(
                        thickness = Dimens.dividerThickness,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                    NavigationBar(
                        modifier = Modifier.height(Dimens.bottomBarHeight),
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        tonalElevation = Dimens.elevationNone
                    ) {
                        NavigationBarItem(
                            selected = currentRoute ==
                                ScreenRoute.MainSearch.route,
                            onClick = {
                                navController.navigate(
                                    ScreenRoute.MainSearch.route
                                ) {
                                    popUpTo(
                                        navController.graph
                                            .findStartDestination()
                                            .id
                                    ) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        R.drawable.ic_main_24dp
                                    ),
                                    contentDescription = stringResource(
                                        R.string.main
                                    )
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(R.string.main)
                                )
                            },
                            colors = navigationItemColors
                        )

                        NavigationBarItem(
                            selected = currentRoute ==
                                ScreenRoute.Favorites.route,
                            onClick = {
                                navController.navigate(
                                    ScreenRoute.Favorites.route
                                ) {
                                    popUpTo(
                                        navController.graph
                                            .findStartDestination()
                                            .id
                                    ) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        R.drawable.ic_favorites_off_24dp
                                    ),
                                    contentDescription = stringResource(
                                        R.string.favorites
                                    )
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(
                                        R.string.favorites
                                    )
                                )
                            },
                            colors = navigationItemColors
                        )

                        NavigationBarItem(
                            selected = currentRoute ==
                                ScreenRoute.Team.route,
                            onClick = {
                                navController.navigate(
                                    ScreenRoute.Team.route
                                ) {
                                    popUpTo(
                                        navController.graph
                                            .findStartDestination()
                                            .id
                                    ) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        R.drawable.ic_team_24dp
                                    ),
                                    contentDescription = stringResource(
                                        R.string.team
                                    )
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(R.string.team)
                                )
                            },
                            colors = navigationItemColors
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavGraph(
            startDestination = ScreenRoute.MainSearch.route,
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
