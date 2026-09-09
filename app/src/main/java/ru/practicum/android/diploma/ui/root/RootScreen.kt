package ru.practicum.android.diploma.ui.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.R
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

    val showBottomBar = currentRoute in bottomBarRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                Column {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                    NavigationBar(
                        modifier = Modifier.height(57.dp),
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ) {
                        NavigationBarItem(
                            selected = currentRoute == ScreenRoute.MainSearch.route,
                            onClick = {
                                navController.navigate(ScreenRoute.MainSearch.route) {
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        R.drawable.ic_main_24dp
                                    ),
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(stringResource(R.string.main))
                            }
                        )

                        NavigationBarItem(
                            selected = currentRoute == ScreenRoute.Favorites.route,
                            onClick = {
                                navController.navigate(ScreenRoute.Favorites.route) {
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        R.drawable.ic_favorites_off_24dp
                                    ),
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(stringResource(R.string.favorites))
                            }
                        )

                        NavigationBarItem(
                            selected = currentRoute == ScreenRoute.Team.route,
                            onClick = {
                                navController.navigate(ScreenRoute.Team.route) {
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        R.drawable.ic_team_24dp
                                    ),
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(stringResource(R.string.team))
                            }
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
