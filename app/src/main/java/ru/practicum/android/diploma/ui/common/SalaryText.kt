package ru.practicum.android.diploma.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.util.SalaryFormatter

@Composable
fun formatSalary(salary: Salary?): String {
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