package ru.practicum.android.diploma.ui.industryselection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.PrimaryButton
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.navigation.ScreenRoute

// Элемент списка отраслей (по макету Figma: List Item 60dp с радиокнопкой)
private val industryItemHeight = 60.dp

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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            SearchField(
                query = state.query,
                onQueryChange = viewModel::onQueryChanged,
                onClearQuery = { viewModel.onQueryChanged("") },
                hint = stringResource(R.string.industry_search_hint),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.spacingL, vertical = Dimens.spacingS)
            )

            IndustryStateContent(
                content = state.content,
                selectedId = state.selected?.id,
                onIndustryClick = viewModel::onIndustryClick,
                modifier = Modifier.weight(1f)
            )

            val selected = state.selected
            PrimaryButton(
                text = stringResource(R.string.choose),
                isVisible = selected != null,
                onClickAction = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(ScreenRoute.SelectionResult.INDUSTRY_KEY, selected)
                    navController.navigateUp()
                },
                modifier = Modifier.padding(
                    start = Dimens.spacingL,
                    end = Dimens.spacingL,
                    bottom = Dimens.spacingXl
                )
            )
        }
    }
}

@Composable
private fun IndustryStateContent(
    content: IndustrySelectionContent,
    selectedId: Int?,
    onIndustryClick: (FilterIndustry) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        when (content) {
            IndustrySelectionContent.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )

            is IndustrySelectionContent.Content -> IndustryList(
                industries = content.industries,
                selectedId = selectedId,
                onIndustryClick = onIndustryClick
            )

            IndustrySelectionContent.Empty -> Placeholder(
                image = R.drawable.placeholder_cat_in_the_shape,
                message = stringResource(R.string.industries_not_found)
            )

            IndustrySelectionContent.NetworkError -> Placeholder(
                image = R.drawable.placeholder_scull,
                message = stringResource(R.string.network_error_message)
            )

            IndustrySelectionContent.ServerError -> Placeholder(
                image = R.drawable.placeholder_crying,
                message = stringResource(R.string.server_error_message)
            )
        }
    }
}

@Composable
private fun IndustryList(
    industries: List<FilterIndustry>,
    selectedId: Int?,
    onIndustryClick: (FilterIndustry) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(industries, key = { it.id }) { industry ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(industryItemHeight)
                    .clickable { onIndustryClick(industry) }
                    .padding(horizontal = Dimens.spacingL),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = industry.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 2,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(
                        if (industry.id == selectedId) {
                            R.drawable.ic_radio_button_on_24dp
                        } else {
                            R.drawable.ic_radio_button_off_24dp
                        }
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
