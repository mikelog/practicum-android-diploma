package ru.practicum.android.diploma.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R

/**
 * Переиспользуемое изображение логотипа компании через Coil.
 * Паттерн позаимствован из VacancyListItem (EP1): во время загрузки
 * и при ошибке показывается плейсхолдер placeholder_vacancy_32dp.
 */
@Composable
fun CompanyLogo(
    model: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        placeholder = painterResource(R.drawable.placeholder_vacancy_32dp),
        error = painterResource(R.drawable.placeholder_vacancy_32dp),
        fallback = painterResource(R.drawable.placeholder_vacancy_32dp),
        alignment = Alignment.Center,
        modifier = modifier
    )
}