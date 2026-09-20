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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.PrimaryButton
import ru.practicum.android.diploma.ui.components.ResetButton
import ru.practicum.android.diploma.ui.components.SalaryTextField
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.navigation.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteringSettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.filtering_settings)) },
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
            // Зашлушка:
            Button(
                onClick = { navController.navigate(ScreenRoute.IndustrySelection.route) }
            ) {
                Text(text = "Выбор отрасли")
            }

            val salaryState = rememberTextFieldState()

            SalaryTextField(
                state = salaryState,
                labelText = stringResource(R.string.expected_salary),
                placeholderText = stringResource(R.string.enter_the_amount),
                modifier = Modifier
                    .padding(top = Dimens.spacingXl)
                    .height(Dimens.salaryFilterField)
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            PrimaryButton(
                text = stringResource(R.string.apply),
                isVisible = true,
                onClickAction = {}, // Применить параметры фильтра
                modifier = Modifier
                    .padding(bottom = Dimens.spacingS, start = Dimens.spacingXxs, end = Dimens.spacingXxs)
            )

            ResetButton(
                text = stringResource(R.string.reset),
                isVisible = true,
                onClickAction = {}, // Сбросить параметры фильтра
                modifier = Modifier
                    .padding(bottom = Dimens.spacingXl, start = Dimens.spacingXxs, end = Dimens.spacingXxs)
            )

        }
    }
}
