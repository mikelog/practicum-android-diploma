package ru.practicum.android.diploma.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancyResponseDto

interface VacancyApiService {

    @GET("areas")
    fun getAreas(): Call<List<FilterAreaDto>>

    @GET("industries")
    fun getIndustries(): Call<List<FilterIndustryDto>>

    @GET("vacancies")
    fun getVacancies(
        @Query("area") area: Int? = null,
        @Query("industry") industry: Int? = null,
        @Query("text") text: String? = null,
        @Query("salary") salary: Int? = null,
        @Query("page") page: Int? = null,
        @Query("only_with_salary") onlyWithSalary: Boolean? = null,
    ): Call<VacancyResponseDto>

    @GET("vacancies/{id}")
    fun getVacancyDetail(
        @Path("id") id: String,
    ): Call<VacancyDetailDto>
}
