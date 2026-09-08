package ru.practicum.android.diploma.ui.countrySelection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.util.navigation.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySelectionScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.сountry_selection)) },
                expandedHeight = 64.dp
            )
        }
    ) { innerPadding ->
        Column( modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Зашлушка:
            Button(
                onClick = { navController.navigate(ScreenRoute.IndustrySelection.route) }
            ) {
                Text(text = "NEXT")
            }
        }
    }
}
