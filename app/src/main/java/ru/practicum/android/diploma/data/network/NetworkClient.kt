package ru.practicum.android.diploma.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import java.io.IOException
import java.util.concurrent.TimeUnit

object NetworkClient {

    private const val BASE_URL = "https://android-diploma.education-services.ru/"
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val CONNECT_TIMEOUT_SECONDS = 30L
    private const val READ_TIMEOUT_SECONDS = 30L
    private const val NO_INTERNET_CODE = -1

    private val authInterceptor = Interceptor { chain ->
        val token = BuildConfig.API_ACCESS_TOKEN
        val request = chain.request().newBuilder()
            .header(AUTHORIZATION_HEADER, token)
            .build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val vacancyApiService: VacancyApiService by lazy {
        retrofit.create(VacancyApiService::class.java)
    }

    suspend fun <T> doRequest(
        connectivityChecker: ConnectivityChecker,
        apiCall: suspend () -> T,
    ): Resource<T> = if (!connectivityChecker.isNetworkAvailable()) {
        Resource.Error(code = NO_INTERNET_CODE)
    } else {
        try {
            Resource.Success(apiCall())
        } catch (e: HttpException) {
            Resource.Error(message = e.message(), code = e.code())
        } catch (e: IOException) {
            Resource.Error(message = e.message)
        }
    }
}
