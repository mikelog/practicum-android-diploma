package ru.practicum.android.diploma.ui.mainsearch

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyCard
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
        onQueryChange = viewModel::onQueryChanged,
        onClearQuery = viewModel::onClearQuery,
        onFilterClick = { navController.navigate(ScreenRoute.FilteringSettings.route) },
        onVacancyClick = { navController.navigate(ScreenRoute.Vacancy.route) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainSearchScreenContent(
    query: String,
    content: MainSearchContent,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
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
                    is MainSearchContent.Idle -> PlaceholderState(
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
                        onVacancyClick = onVacancyClick
                    )

                    is MainSearchContent.Empty -> Column(modifier = Modifier.fillMaxSize()) {
                        FoundCountChip(
                            text = stringResource(R.string.no_vacancies_found),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = Dimens.spacingS)
                        )
                        PlaceholderState(
                            image = R.drawable.placeholder_cat_with_a_plate,
                            message = stringResource(R.string.empty_result_message),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    is MainSearchContent.NetworkError -> PlaceholderState(
                        image = R.drawable.placeholder_scull,
                        message = stringResource(R.string.network_error_message)
                    )

                    is MainSearchContent.ServerError -> PlaceholderState(
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
    onVacancyClick: (VacancyCard) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        FoundCountChip(
            text = pluralStringResource(R.plurals.found_vacancies, found, found),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = Dimens.spacingS, bottom = Dimens.spacingS)
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(vacancies, key = { it.id }) { vacancy ->
                VacancyListItem(vacancy = vacancy, onClick = onVacancyClick)
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

@Composable
private fun PlaceholderState(
    @DrawableRes image: Int,
    message: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier.size(
                    width = Dimens.placeholderImageWidth,
                    height = Dimens.placeholderImageHeight
                )
            )
            if (message != null) {
                Text(
                    text = message,
                    modifier = Modifier.padding(
                        top = Dimens.spacingL,
                        start = Dimens.spacingXl,
                        end = Dimens.spacingXl
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(Dimens.searchFieldCornerRadius)
    Box(
        modifier = modifier
            .height(Dimens.searchFieldHeight)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = Dimens.spacingL),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                if (query.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search_hint),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (query.isEmpty()) {
                Box(
                    modifier = Modifier.size(Dimens.searchFieldIconButtonSize),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search_24dp),
                        contentDescription = stringResource(R.string.cd_search_icon),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                IconButton(
                    onClick = onClearQuery,
                    modifier = Modifier.size(Dimens.searchFieldIconButtonSize)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close_24dp),
                        contentDescription = stringResource(R.string.cd_clear_query),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
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
            onQueryChange = {},
            onClearQuery = {},
            onFilterClick = {},
            onVacancyClick = {}
        )
    }
}
