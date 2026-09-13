package ru.practicum.android.diploma.ui.mainsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.navigation.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSearchScreen(
    navController: NavController,
    viewModel: MainSearchViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.vacancy_search)) },
                expandedHeight = Dimens.topBarHeight
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Зашлушка:
            Text(text = stringResource(R.string.main_search_debug_state, state.content::class.simpleName.orEmpty()))
            Button(
                onClick = { navController.navigate(ScreenRoute.FilteringSettings.route) }
            ) {
                Text(text = stringResource(R.string.filtering_settings))
            }
            Button(
                onClick = { navController.navigate(ScreenRoute.Vacancy.route) }
            ) {
                Text(text = stringResource(R.string.vacancy))
            }
        }
    }
}
