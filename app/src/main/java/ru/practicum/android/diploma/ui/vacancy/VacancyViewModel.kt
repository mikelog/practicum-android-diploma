package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailInteractor

class VacancyViewModel(
    private val vacancyId: String,
    private val vacancyDetailInteractor: VacancyDetailInteractor,
    private val favoriteVacancyInteractor: FavoriteVacancyInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow<VacancyContent>(
        VacancyContent.Loading
    )

    val state: StateFlow<VacancyContent> = _state.asStateFlow()

    private var exitHandled = false

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.value = VacancyContent.Loading

            when (
                val result = vacancyDetailInteractor.getDetail(vacancyId)
            ) {
                is Resource.Success -> {
                    val isFavorite =
                        favoriteVacancyInteractor.isFavorite(vacancyId)
                    _state.value = VacancyContent.Content(
                        vacancy = result.data,
                        isFavorite = isFavorite,
                    )
                }

                is Resource.Error -> {
                    when {
                        result.code == HTTP_NOT_FOUND -> {
                            favoriteVacancyInteractor.removeCachedDetail(vacancyId)
                            _state.value = VacancyContent.NotFound
                        }

                        result.isNetworkError() -> {
                            val cached = favoriteVacancyInteractor.getVacancyById(vacancyId)
                            _state.value = if (cached != null) {
                                VacancyContent.Content(
                                    vacancy = cached,
                                    isFavorite = true,
                                )
                            } else {
                                VacancyContent.Error(
                                    messageRes = R.string.network_error_message
                                )
                            }
                        }

                        else -> {
                            _state.value = VacancyContent.Error(
                                messageRes = R.string.server_error_message
                            )
                        }
                    }
                }

                Resource.Loading -> {
                    _state.value = VacancyContent.Loading
                }
            }
        }
    }

    fun toggleFavorite() {
        _state.update { currentState ->
            if (currentState is VacancyContent.Content) {
                currentState.copy(
                    isFavorite = !currentState.isFavorite
                )
            } else {
                currentState
            }
        }
    }

    fun saveFavoriteAndExit(
        onFinished: () -> Unit,
    ) {
        if (exitHandled) {
            return
        }
        exitHandled = true
        val content = _state.value as? VacancyContent.Content
        if (content == null) {
            onFinished()
            return
        }
        viewModelScope.launch {
            try {
                if (content.isFavorite) {
                    favoriteVacancyInteractor.addVacancy(
                        content.vacancy
                    )
                } else {
                    favoriteVacancyInteractor.removeVacancy(
                        vacancyId
                    )
                }
            } finally {
                onFinished()
            }
        }
    }

    private fun Resource.Error.isNetworkError(): Boolean =
        code == null || code == NETWORK_ERROR_CODE

    private companion object {
        const val HTTP_NOT_FOUND = 404
        const val NETWORK_ERROR_CODE = -1
    }
}
