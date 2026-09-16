package ru.practicum.android.diploma.ui.vacancy

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.SalaryFormatter

val vacancyId = "0000258d-fb45-3152-bfeb-250a4c547384"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyScreen(
    navController: NavController,
    // vacancyId: String,
    viewModel: VacancyViewModel = koinViewModel(
        parameters = { parametersOf(vacancyId) }
    ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    VacancyScreenContent(
        state = state, //     VacancyContent.Content(vacancy = previewVacancy),
        onBackClick = navController::navigateUp,
        onFavoriteClick = {
            // TODO: Добавить вакансию в избранное
        },
        onShareClick = {
            // TODO: Открыть системное меню «Поделиться»
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VacancyScreenContent(
    state: VacancyContent,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back_24dp),
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.vacancy)
                    )
                },
                actions = {
                    IconButton(onClick = onShareClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_sharing_24dp),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_favorites_off_24dp),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                expandedHeight = Dimens.topBarHeight
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (state) {
                VacancyContent.Loading -> {
                    VacancyLoading()
                }

                is VacancyContent.Content -> {
                    VacancyDetails(
                        vacancy = state.vacancy,
                    )
                }

                is VacancyContent.Error -> {
                    // Обработку ошибки добавим позже.
                    // Пока основная область экрана останется пустой.
                    Placeholder(
                        image = R.drawable.placeholder_cat_in_the_shape,
                        message = stringResource(R.string.server_error_message)
                    )
                }
            }
        }
    }
}

@Composable
private fun VacancyLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun VacancyDetails(
    vacancy: VacancyDetail,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.spacingL)
    ) {
        Text(
            text = vacancy.name, // тут будет VacancyDetail.name
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = Dimens.spacingXl)
        )

        Text(
            text = salaryText(vacancy.salary), // тут будет VacancyDetail.salary
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = Dimens.spacingXs)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.spacingXl)
                .height(Dimens.cardHeight)
                .clip(RoundedCornerShape(Dimens.spacingM))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(all = Dimens.spacingL)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = vacancy.employer.logo,
                    contentDescription = vacancy.employer.name,
                    placeholder = painterResource(
                        R.drawable.placeholder_vacancy_32dp
                    ),
                    error = painterResource(
                        R.drawable.placeholder_vacancy_32dp
                    ),
                    fallback = painterResource(
                        R.drawable.placeholder_vacancy_32dp
                    ),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(Dimens.vacancyLogoSize)
                        .clip(
                            RoundedCornerShape(
                                Dimens.vacancyLogoCornerRadius
                            )
                        )
                        .border(
                            width = Dimens.vacancyLogoBorderWidth,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(
                                Dimens.vacancyLogoCornerRadius
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = Dimens.spacingS)
                ) {
                    Text(
                        text = vacancy.employer.name, // тут будет VacancyDetail.employer
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        // Под названием компании должен отображаться её адрес. Если адреса нет, то должно отображаться название региона.
                        text = vacancy.area.name, // тут будет VacancyDetail.area
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(top = Dimens.spacingXl)
                .verticalScroll(rememberScrollState())

        ) {
            Text(
                text = stringResource(R.string.experience),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = vacancy.experience ?: "", // тут будет VacancyDetail.experience
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = Dimens.spacingXs)
            )

            Text(
                text = vacancy.schedule ?: "", // тут будет VacancyDetail.schedule
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = Dimens.spacingXs)
            )

            VacancyDescription(vacancy.description)
        }
    }

}

@Composable
private fun salaryText(salary: Salary?): String {
    val from = salary?.from
    val to = salary?.to
    return when {
        from == null && to == null -> stringResource(R.string.salary_not_specified)
        from != null && to != null -> stringResource(
            R.string.salary_range,
            SalaryFormatter.formatValue(from, salary.currency),
            SalaryFormatter.formatValue(to, salary.currency)
        )
        from != null -> stringResource(
            R.string.salary_from,
            SalaryFormatter.formatValue(from, salary.currency)
        )
        else -> stringResource(
            R.string.salary_to,
            SalaryFormatter.formatValue(requireNotNull(to), salary.currency)
        )
    }
}

@Composable
private fun VacancyDescription(
    description: String?,
) {
    if (description.isNullOrBlank()) {
        return
    }

    val formattedDescription = remember(description) {
        AnnotatedString.fromHtml(description)
    }

    Text(
        text = formattedDescription,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = Dimens.spacingXxl),
    )
}

// -----------------------------------Preview
@Preview(
    name = "Vacancy — Loading",
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun VacancyScreenLoadingPreview() {
    MaterialTheme {
        VacancyScreenContent(
            state = VacancyContent.Loading,
            onBackClick = {},
            onFavoriteClick = {},
            onShareClick = {},
        )
    }
}

@Preview(
    name = "Vacancy — Content",
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun VacancyScreenContentPreview() {
    MaterialTheme {
        VacancyScreenContent(
            state = VacancyContent.Content(
                vacancy = previewVacancy,
            ),
            onBackClick = {},
            onFavoriteClick = {},
            onShareClick = {},
        )
    }
}

@Preview(
    name = "Vacancy — Error",
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun VacancyScreenErrorPreview() {
    MaterialTheme {
        VacancyScreenContent(
            state = VacancyContent.Error(
                messageRes = R.string.server_error_message,
            ),
            onBackClick = {},
            onFavoriteClick = {},
            onShareClick = {},
        )
    }
}
