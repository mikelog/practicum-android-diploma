package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.api.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDetailInteractorImpl(
    private val vacancyDetailRepository: VacancyDetailRepository,
) : VacancyDetailInteractor {
    override suspend fun getDetail(id: String): Resource<VacancyDetail> {
        return vacancyDetailRepository.getDetail(id)
    }
}
