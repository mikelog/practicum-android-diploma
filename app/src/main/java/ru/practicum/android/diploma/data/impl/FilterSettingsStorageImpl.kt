package ru.practicum.android.diploma.data.impl

import android.content.Context
import androidx.core.content.edit
import ru.practicum.android.diploma.domain.api.FilterSettingsStorage
import ru.practicum.android.diploma.domain.models.FilterParameters

class FilterSettingsStorageImpl(
    context: Context,
) : FilterSettingsStorage {

    private val preferences = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun get(): FilterParameters {
        return FilterParameters(
            industryId = if (preferences.contains(KEY_INDUSTRY)) {
                preferences.getInt(KEY_INDUSTRY, 0)
            } else {
                null
            },
            salary = if (preferences.contains(KEY_SALARY)) {
                preferences.getInt(KEY_SALARY, 0)
            } else {
                null
            },
            onlyWithSalary = preferences.getBoolean(
                KEY_ONLY_WITH_SALARY,
                false,
            ),
        )
    }

    override fun save(parameters: FilterParameters) {
        preferences.edit {
            if (parameters.industryId != null) {
                putInt(KEY_INDUSTRY, parameters.industryId)
            } else {
                remove(KEY_INDUSTRY)
            }

            if (parameters.salary != null) {
                putInt(KEY_SALARY, parameters.salary)
            } else {
                remove(KEY_SALARY)
            }

            putBoolean(KEY_ONLY_WITH_SALARY, parameters.onlyWithSalary)
        }
    }

    override fun clear() {
        preferences.edit {
            remove(KEY_INDUSTRY)
            remove(KEY_SALARY)
            remove(KEY_ONLY_WITH_SALARY)
        }
    }

    private companion object {
        const val PREFS_NAME = "filter_parameters"
        const val KEY_INDUSTRY = "industry"
        const val KEY_SALARY = "salary"
        const val KEY_ONLY_WITH_SALARY = "only_with_salary"
    }
}
