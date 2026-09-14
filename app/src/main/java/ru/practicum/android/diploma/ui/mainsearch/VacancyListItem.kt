package ru.practicum.android.diploma.ui.mainsearch

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyCard
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.SalaryFormatter

/**
 * Переиспользуемая карточка вакансии для списков (поиск, избранное).
 */
@Composable
fun VacancyListItem(
    vacancy: VacancyCard,
    onClick: (VacancyCard) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(vacancy) }
            .padding(horizontal = Dimens.spacingL, vertical = Dimens.vacancyCardVerticalPadding),
        verticalAlignment = Alignment.Top
    ) {
        val logoShape = RoundedCornerShape(Dimens.vacancyLogoCornerRadius)
        Box(
            modifier = Modifier
                .size(Dimens.vacancyLogoSize)
                .clip(logoShape)
                .background(MaterialTheme.colorScheme.surface)
                .border(Dimens.vacancyLogoBorderWidth, MaterialTheme.colorScheme.outlineVariant, logoShape)
        ) {
            AsyncImage(
                model = vacancy.logo,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.placeholder_vacancy_32dp),
                error = painterResource(R.drawable.placeholder_vacancy_32dp),
                fallback = painterResource(R.drawable.placeholder_vacancy_32dp),
                modifier = Modifier
                    .padding(Dimens.spacingS)
                    .size(Dimens.spacingXxl)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = Dimens.spacingM)
        ) {
            val nameAndCity = listOfNotNull(vacancy.name, vacancy.city).joinToString(", ")
            Text(
                text = nameAndCity,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (vacancy.company != null) {
                Text(
                    text = vacancy.company,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = salaryText(vacancy.salary),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
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

@Preview(showBackground = true)
@Composable
private fun VacancyListItemPreview() {
    AppTheme {
        VacancyListItem(
            vacancy = VacancyCard(
                id = "1",
                name = "Android-разработчик (Kotlin)",
                company = "Яндекс",
                city = "Москва",
                salary = Salary(from = 150_000, to = 250_000, currency = "RUR"),
                logo = null
            ),
            onClick = {}
        )
    }
}
