package ru.practicum.android.diploma.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.practicum.android.diploma.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.domain.models.VacancyCard
import ru.practicum.android.diploma.domain.models.VacancyDetail

class FavoritesViewModel(
    favoriteVacancyInteractor: FavoriteVacancyInteractor
) : ViewModel() {

    val state: StateFlow<FavoritesContent> = favoriteVacancyInteractor.getAllVacancies()
        .map { vacancies ->
            if (vacancies.isEmpty()) {
                FavoritesContent.Empty
            } else {
                FavoritesContent.Content(vacancies.map { it.toVacancyCard() }.toImmutableList())
            }
        }
        .catch { emit(FavoritesContent.Error) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MS),
            initialValue = FavoritesContent.Loading
        )

    private fun VacancyDetail.toVacancyCard() = VacancyCard(
        id = id,
        name = name,
        company = employer.name,
        city = address?.city,
        salary = salary,
        logo = employer.logo
    )

    private companion object {
        const val STOP_TIMEOUT_MS = 5_000L
    }
}
