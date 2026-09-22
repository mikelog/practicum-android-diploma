package ru.practicum.android.diploma.ui.vacancy

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.ui.common.formatSalary
import ru.practicum.android.diploma.ui.components.CompanyLogo
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.theme.Dimens

// Экран деталей вакансии: карточка работодателя
private val cardHeight = 80.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyScreen(
    navController: NavController,
    vacancyId: String,
    viewModel: VacancyViewModel = koinViewModel(
        parameters = { parametersOf(vacancyId) }
    ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    fun leaveScreen() {
        viewModel.saveFavoriteAndExit {
            navController.navigateUp()
        }
    }

    BackHandler(
        onBack = ::leaveScreen
    )

    VacancyScreenContent(
        state = state,
        onBackClick = ::leaveScreen,
        onFavoriteClick = viewModel::toggleFavorite,
        onShareClick = {
            (state as? VacancyContent.Content)?.let { content ->
                shareVacancy(
                    context = context,
                    url = content.vacancy.url,
                )
            }
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
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.vacancy)
                    )
                },
                actions = {
                    if (state is VacancyContent.Content) {
                        IconButton(onClick = onShareClick) {
                            Icon(
                                painter = painterResource(R.drawable.ic_sharing_24dp),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }

                        IconButton(onClick = onFavoriteClick) {
                            Icon(
                                painter = painterResource(
                                    if (state.isFavorite) {
                                        R.drawable.ic_favorites_on_24dp
                                    } else {
                                        R.drawable.ic_favorites_off_24dp
                                    }
                                ),
                                contentDescription = null,
                                tint = if (state.isFavorite) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.onBackground
                                },
                            )
                        }
                    }
                },
                expandedHeight = Dimens.topBarHeight,
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

                VacancyContent.NetworkError -> {
                    Placeholder(
                        image = R.drawable.placeholder_scull,
                        message = stringResource(R.string.network_error_message)
                    )
                }

                VacancyContent.ServerError -> {
                    Placeholder(
                        image = R.drawable.placeholder_crying,
                        message = stringResource(R.string.server_error_message)
                    )
                }

                VacancyContent.NotFound -> {
                    Placeholder(
                        image = R.drawable.placeholder_dancing_phone,
                        message = stringResource(R.string.vacancy_not_found_or_has_been_removed),
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
            text = vacancy.name,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = Dimens.spacingXl)
        )

        Text(
            text = formatSalary(vacancy.salary),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = Dimens.spacingXs)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.spacingXl)
                .height(cardHeight)
                .clip(RoundedCornerShape(Dimens.spacingM))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(all = Dimens.spacingL)
                    .fillMaxWidth()
            ) {
                CompanyLogo(
                    model = vacancy.employer.logo,
                    contentDescription = vacancy.employer.name,
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
                        text = vacancy.employer.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = vacancy.address
                            ?.raw
                            ?.takeIf { it.isNotBlank() }
                            ?: vacancy.area.name,
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
                text = vacancy.experience ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = Dimens.spacingXs)
            )

            val employmentAndSchedule = listOfNotNull(
                vacancy.employment?.takeIf { it.isNotBlank() },
                vacancy.schedule?.takeIf { it.isNotBlank() },
            ).joinToString(separator = ",А ")

            if (employmentAndSchedule.isNotBlank()) {
                Text(
                    text = employmentAndSchedule,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            VacancyDescription(
                description = vacancy.description,
                modifier = Modifier.padding(top = Dimens.spacingXxl)
            )

            SkillsSection(
                skills = vacancy.skills,
                modifier = Modifier.padding(top = Dimens.spacingXxl)
            )

            ContactsSection(
                contacts = vacancy.contacts,
                modifier = Modifier.padding(top = Dimens.spacingXxl)
            )
        }
    }
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
            state = VacancyContent.ServerError,
            onBackClick = {},
            onFavoriteClick = {},
            onShareClick = {},
        )
    }
}
