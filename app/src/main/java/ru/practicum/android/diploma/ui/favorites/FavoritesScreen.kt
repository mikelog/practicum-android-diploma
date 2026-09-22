package ru.practicum.android.diploma.ui.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyCard
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.mainsearch.VacancyListItem
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.navigation.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FavoritesScreenContent(
        content = state,
        onVacancyClick = { navController.navigate(ScreenRoute.Vacancy.createRoute(it.id)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesScreenContent(
    content: FavoritesContent,
    onVacancyClick: (VacancyCard) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.favorites)) },
                expandedHeight = Dimens.topBarHeight
            )
        }
    ) { innerPadding ->
        when (content) {
            is FavoritesContent.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )

            is FavoritesContent.Content -> LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(content.vacancies, key = { it.id }) { vacancy ->
                    VacancyListItem(vacancy = vacancy, onClick = onVacancyClick)
                }
            }

            is FavoritesContent.Empty -> Placeholder(
                image = R.drawable.placeholder_phone_with_magnifying_glass,
                message = stringResource(R.string.favorites_empty_message),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            is FavoritesContent.Error -> Placeholder(
                image = R.drawable.placeholder_cat_with_a_plate,
                message = stringResource(R.string.empty_result_message),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}

private val previewVacancies = listOf(
    VacancyCard(
        id = "1",
        name = "Android-разработчик, Москва",
        company = "Еда",
        city = "Москва",
        salary = Salary(from = 100_000, to = null, currency = "RUR"),
        logo = null
    ),
    VacancyCard(
        id = "2",
        name = "Java-разработчик, Омск",
        company = "Маркет",
        city = "Омск",
        salary = null,
        logo = null
    )
).toImmutableList()

@Preview(showBackground = true, name = "Loading")
@Composable
private fun FavoritesScreenLoadingPreview() {
    AppTheme {
        FavoritesScreenContent(content = FavoritesContent.Loading, onVacancyClick = {})
    }
}

@Preview(showBackground = true, name = "Content")
@Composable
private fun FavoritesScreenContentPreview() {
    AppTheme {
        FavoritesScreenContent(content = FavoritesContent.Content(previewVacancies), onVacancyClick = {})
    }
}

@Preview(showBackground = true, name = "Empty")
@Composable
private fun FavoritesScreenEmptyPreview() {
    AppTheme {
        FavoritesScreenContent(content = FavoritesContent.Empty, onVacancyClick = {})
    }
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun FavoritesScreenErrorPreview() {
    AppTheme {
        FavoritesScreenContent(content = FavoritesContent.Error, onVacancyClick = {})
    }
}
