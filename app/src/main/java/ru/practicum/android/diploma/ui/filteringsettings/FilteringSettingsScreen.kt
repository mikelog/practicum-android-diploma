package ru.practicum.android.diploma.ui.filteringsettings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.ui.components.PrimaryButton
import ru.practicum.android.diploma.ui.components.ResetButton
import ru.practicum.android.diploma.ui.components.SalaryTextField
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.navigation.ScreenRoute

private val salaryFilterFieldHeight = 51.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteringSettingsScreen(
    navController: NavController,
    viewModel: FilteringSettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val salaryState = rememberTextFieldState(
        initialText = uiState.parameters.salary
            ?.toString()
            .orEmpty()
    )

    val isButtonsVisible = salaryState.text
        .toString()
        .isNotBlank()

    LaunchedEffect(uiState.parameters.salary) {
        val expectedText = uiState.parameters.salary
            ?.toString()
            .orEmpty()

        if (salaryState.text.toString() != expectedText) {
            salaryState.edit {
                replace(
                    start = 0,
                    end = length,
                    text = expectedText
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            R.string.filtering_settings
                        )
                    )
                },
                expandedHeight = Dimens.topBarHeight
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = Dimens.spacingL)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate(
                        ScreenRoute.IndustrySelection.route
                    )
                }
            ) {
                Text(text = "Выбор отрасли")
            }

            SalaryTextField(
                state = salaryState,
                labelText = stringResource(
                    R.string.expected_salary
                ),
                placeholderText = stringResource(
                    R.string.enter_the_amount
                ),
                modifier = Modifier
                    .padding(top = Dimens.spacingXl)
                    .height(salaryFilterFieldHeight)
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            PrimaryButton(
                text = stringResource(R.string.apply),
                isVisible = isButtonsVisible,
                onClickAction = {
                    val parameters = FilterParameters(
                        /*
                         * Пока логика выбора отрасли не реализована.
                         */
                        industryId = null,
                        salary = salaryState.text
                            .toString()
                            .toIntOrNull(),

                        /*
                         * Пока логика переключателя
                         * не реализована.
                         */
                        onlyWithSalary = false
                    )

                    viewModel.onApplyClicked(parameters)
                },
                modifier = Modifier.padding(
                    bottom = Dimens.spacingS,
                    start = Dimens.spacingXxs,
                    end = Dimens.spacingXxs
                )
            )

            ResetButton(
                text = stringResource(R.string.reset),
                isVisible = isButtonsVisible,
                onClickAction = {
                    salaryState.edit {
                        replace(
                            start = 0,
                            end = length,
                            text = ""
                        )
                    }
                    viewModel.onResetClicked()
                },
                modifier = Modifier.padding(
                    bottom = Dimens.spacingXl,
                    start = Dimens.spacingXxs,
                    end = Dimens.spacingXxs
                )
            )
        }
    }
}
