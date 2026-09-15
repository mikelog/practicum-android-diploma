package ru.practicum.android.diploma.ui.mainsearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyCard
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.navigation.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSearchScreen(
    navController: NavController,
    viewModel: MainSearchViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    MainSearchScreenContent(
        query = state.query,
        content = state.content,
        isNextPageLoading = state.isNextPageLoading,
        onQueryChange = viewModel::onQueryChanged,
        onClearQuery = viewModel::onQueryCleared,
        onListScrolledToEnd = viewModel::onListScrolledToEnd,
        onFilterClick = { navController.navigate(ScreenRoute.FilteringSettings.route) },
        onVacancyClick = { navController.navigate(ScreenRoute.Vacancy.route) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainSearchScreenContent(
    query: String,
    content: MainSearchContent,
    isNextPageLoading: Boolean,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onListScrolledToEnd: () -> Unit,
    onFilterClick: () -> Unit,
    onVacancyClick: (VacancyCard) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.vacancy_search)) },
                actions = {
                    IconButton(onClick = onFilterClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_filter_off_24dp),
                            contentDescription = stringResource(R.string.filtering_settings)
                        )
                    }
                },
                expandedHeight = Dimens.topBarHeight
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            SearchField(
                query = query,
                onQueryChange = onQueryChange,
                onClearQuery = onClearQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.spacingL, vertical = Dimens.spacingS)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (content) {
                    is MainSearchContent.Idle -> Placeholder(
                        image = R.drawable.placeholder_man_with_binoculars,
                        message = null
                    )

                    is MainSearchContent.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )

                    is MainSearchContent.Content -> ResultsState(
                        vacancies = content.vacancies,
                        found = content.found,
                        isNextPageLoading = isNextPageLoading,
                        onListScrolledToEnd = onListScrolledToEnd,
                        onVacancyClick = onVacancyClick
                    )

                    is MainSearchContent.Empty -> Column(modifier = Modifier.fillMaxSize()) {
                        FoundCountChip(
                            text = stringResource(R.string.no_vacancies_found),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = Dimens.chipTopSpacing)
                        )
                        Placeholder(
                            image = R.drawable.placeholder_cat_with_a_plate,
                            message = stringResource(R.string.empty_result_message),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    is MainSearchContent.NetworkError -> Placeholder(
                        image = R.drawable.placeholder_scull,
                        message = stringResource(R.string.network_error_message)
                    )

                    is MainSearchContent.ServerError -> Placeholder(
                        image = R.drawable.placeholder_crying,
                        message = stringResource(R.string.server_error_message)
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultsState(
    vacancies: List<VacancyCard>,
    found: Int,
    isNextPageLoading: Boolean,
    onListScrolledToEnd: () -> Unit,
    onVacancyClick: (VacancyCard) -> Unit
) {
    val listState = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleIndex >= layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) onListScrolledToEnd()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        FoundCountChip(
            text = pluralStringResource(R.plurals.found_vacancies, found, found),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = Dimens.chipTopSpacing, bottom = Dimens.spacingS)
        )
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(vacancies, key = { it.id }) { vacancy ->
                VacancyListItem(vacancy = vacancy, onClick = onVacancyClick)
            }
            if (isNextPageLoading) {
                item(key = "next_page_loading") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.spacingL),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
private fun FoundCountChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.chipCornerRadius))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = Dimens.chipHorizontalPadding, vertical = Dimens.chipVerticalPadding)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private const val PREVIEW_QUERY = "Разработчик"

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
)

@Preview(showBackground = true, name = "Idle")
@Composable
private fun MainSearchScreenIdlePreview() {
    AppTheme {
        MainSearchScreenContent(
            query = "",
            content = MainSearchContent.Idle,
            isNextPageLoading = false,
            onListScrolledToEnd = {},
            onQueryChange = {},
            onClearQuery = {},
            onFilterClick = {},
            onVacancyClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading")
@Composable
private fun MainSearchScreenLoadingPreview() {
    AppTheme {
        MainSearchScreenContent(
            query = "Android",
            content = MainSearchContent.Loading,
            isNextPageLoading = false,
            onListScrolledToEnd = {},
            onQueryChange = {},
            onClearQuery = {},
            onFilterClick = {},
            onVacancyClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Content")
@Composable
private fun MainSearchScreenContentPreview() {
    AppTheme {
        MainSearchScreenContent(
            query = PREVIEW_QUERY,
            content = MainSearchContent.Content(vacancies = previewVacancies, found = 286),
            isNextPageLoading = false,
            onListScrolledToEnd = {},
            onQueryChange = {},
            onClearQuery = {},
            onFilterClick = {},
            onVacancyClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty")
@Composable
private fun MainSearchScreenEmptyPreview() {
    AppTheme {
        MainSearchScreenContent(
            query = "Абвгд",
            content = MainSearchContent.Empty,
            isNextPageLoading = false,
            onListScrolledToEnd = {},
            onQueryChange = {},
            onClearQuery = {},
            onFilterClick = {},
            onVacancyClick = {}
        )
    }
}

@Preview(showBackground = true, name = "NetworkError")
@Composable
private fun MainSearchScreenNetworkErrorPreview() {
    AppTheme {
        MainSearchScreenContent(
            query = PREVIEW_QUERY,
            content = MainSearchContent.NetworkError,
            isNextPageLoading = false,
            onListScrolledToEnd = {},
            onQueryChange = {},
            onClearQuery = {},
            onFilterClick = {},
            onVacancyClick = {}
        )
    }
}

@Preview(showBackground = true, name = "ServerError")
@Composable
private fun MainSearchScreenServerErrorPreview() {
    AppTheme {
        MainSearchScreenContent(
            query = PREVIEW_QUERY,
            content = MainSearchContent.ServerError,
            isNextPageLoading = false,
            onListScrolledToEnd = {},
            onQueryChange = {},
            onClearQuery = {},
            onFilterClick = {},
            onVacancyClick = {}
        )
    }
}
