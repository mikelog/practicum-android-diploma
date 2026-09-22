package ru.practicum.android.diploma.ui.industryselection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.navigation.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndustrySelectionScreen(
    navController: NavController,
    viewModel: IndustrySelectionViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back_24dp),
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.industry_selection))
                },
                expandedHeight = Dimens.topBarHeight
            )
        }
    ) { innerPadding ->
        val currentState = state
        when (currentState) {
            IndustrySelectionContent.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )

            is IndustrySelectionContent.Content -> IndustryList(
                industries = currentState.industries,
                onIndustryClick = { industry ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(ScreenRoute.SelectionResult.INDUSTRY_KEY, industry)
                    navController.navigateUp()
                },
                modifier = Modifier.padding(innerPadding)
            )

            IndustrySelectionContent.Empty -> Placeholder(
                image = R.drawable.placeholder_cat_in_the_shape,
                message = stringResource(R.string.industries_not_found),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            IndustrySelectionContent.NetworkError -> Placeholder(
                image = R.drawable.placeholder_scull,
                message = stringResource(R.string.network_error_message),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            IndustrySelectionContent.ServerError -> Placeholder(
                image = R.drawable.placeholder_crying,
                message = stringResource(R.string.server_error_message),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun IndustryList(
    industries: List<FilterIndustry>,
    onIndustryClick: (FilterIndustry) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(industries, key = { it.id }) { industry ->
            Text(
                text = industry.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onIndustryClick(industry) }
                    .padding(
                        horizontal = Dimens.spacingL,
                        vertical = Dimens.spacingM
                    )
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = Dimens.spacingL),
                thickness = Dimens.dividerThickness,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}