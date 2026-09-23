package ru.practicum.android.diploma.ui.filteringsettings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.ui.components.PrimaryButton
import ru.practicum.android.diploma.ui.components.ResetButton
import ru.practicum.android.diploma.ui.components.SalaryTextField
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.navigation.ScreenRoute

private val salaryFilterFieldHeight = 51.dp

// Пункт фильтра («Отрасль») и строка с чекбоксом (по макету Figma: List Item 60dp)
private val filterItemHeight = 60.dp
private val iconSize = 24.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteringSettingsScreen(
    navController: NavController,
    viewModel: FilteringSettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val parameters = uiState.parameters

    val salaryState = rememberTextFieldState(
        initialText = parameters.salary?.toString().orEmpty()
    )

    // Каждое изменение поля зарплаты сразу уходит во ViewModel и сохраняется
    LaunchedEffect(salaryState, viewModel) {
        snapshotFlow { salaryState.text.toString() }
            .collect(viewModel::onSalaryChanged)
    }

    // Результат экрана выбора отрасли
    LaunchedEffect(navController, viewModel) {
        navController.currentBackStackEntry?.savedStateHandle?.let { savedStateHandle ->
            savedStateHandle
                .getStateFlow<FilterIndustry?>(ScreenRoute.SelectionResult.INDUSTRY_KEY, null)
                .collect { industry ->
                    if (industry != null) {
                        viewModel.onIndustrySelected(industry)
                        savedStateHandle.remove<FilterIndustry>(ScreenRoute.SelectionResult.INDUSTRY_KEY)
                    }
                }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back_24dp),
                            contentDescription = null,
                        )
                    }
                },
                title = { Text(text = stringResource(R.string.filtering_settings)) },
                expandedHeight = Dimens.topBarHeight
            )
        }
    ) { innerPadding ->
        FilteringSettingsContent(
            parameters = parameters,
            salaryState = salaryState,
            onIndustryClick = { navController.navigate(ScreenRoute.IndustrySelection.route) },
            onIndustryClear = viewModel::onIndustryCleared,
            onOnlyWithSalaryToggle = viewModel::onOnlyWithSalaryToggled,
            onApplyClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(ScreenRoute.SelectionResult.FILTER_APPLIED_KEY, true)
                navController.popBackStack()
            },
            onResetClick = {
                salaryState.clearText()
                viewModel.onResetClicked()
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun FilteringSettingsContent(
    parameters: FilterParameters,
    salaryState: TextFieldState,
    onIndustryClick: () -> Unit,
    onIndustryClear: () -> Unit,
    onOnlyWithSalaryToggle: () -> Unit,
    onApplyClick: () -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isButtonsVisible = !parameters.isEmpty

    Column(
        modifier = modifier
            .padding(horizontal = Dimens.spacingL)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilterItem(
            label = stringResource(R.string.industry),
            value = parameters.industry?.name,
            onClick = onIndustryClick,
            onClear = onIndustryClear,
        )

        SalaryTextField(
            state = salaryState,
            labelText = stringResource(R.string.expected_salary),
            placeholderText = stringResource(R.string.enter_the_amount),
            modifier = Modifier
                .padding(top = Dimens.spacingXl)
                .height(salaryFilterFieldHeight)
        )

        OnlyWithSalaryRow(
            checked = parameters.onlyWithSalary,
            onToggle = onOnlyWithSalaryToggle,
            modifier = Modifier.padding(top = Dimens.spacingXl)
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = stringResource(R.string.apply),
            isVisible = isButtonsVisible,
            onClickAction = onApplyClick,
            modifier = Modifier.padding(
                bottom = Dimens.spacingS,
                start = Dimens.spacingXxs,
                end = Dimens.spacingXxs
            )
        )

        ResetButton(
            text = stringResource(R.string.reset),
            isVisible = isButtonsVisible,
            onClickAction = onResetClick,
            modifier = Modifier.padding(
                bottom = Dimens.spacingXl,
                start = Dimens.spacingXxs,
                end = Dimens.spacingXxs
            )
        )
    }
}

/**
 * Пункт фильтра: пока значение не выбрано — серый заголовок и стрелка,
 * после выбора — маленький заголовок, значение и крестик для очистки.
 */
@Composable
private fun FilterItem(
    label: String,
    value: String?,
    onClick: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(filterItemHeight)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            if (value.isNullOrEmpty()) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            } else {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (value.isNullOrEmpty()) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_forward_24dp),
                contentDescription = stringResource(R.string.cd_open),
                modifier = Modifier.size(iconSize),
                tint = MaterialTheme.colorScheme.onBackground,
            )
        } else {
            CompositionLocalProvider(
                LocalMinimumInteractiveComponentSize provides 0.dp
            ) {
                IconButton(
                    onClick = onClear,
                    modifier = Modifier.size(iconSize),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close_24dp),
                        contentDescription = stringResource(R.string.cd_clear_value),
                        modifier = Modifier.size(iconSize),
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
    }
}

@Composable
private fun OnlyWithSalaryRow(
    checked: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(filterItemHeight)
            .clickable(onClick = onToggle),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.only_with_salary),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(
                if (checked) R.drawable.ic_check_box_on_24dp else R.drawable.ic_check_box_off_24dp
            ),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
