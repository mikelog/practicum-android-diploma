package ru.practicum.android.diploma

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.practicum.android.diploma.di.koinModules

class App : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(this@App)
            modules(koinModules)
        }
    }

    override fun newImageLoader(): ImageLoader {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header(USER_AGENT_HEADER, USER_AGENT_VALUE)
                    .build()
                chain.proceed(request)
            }
            .build()

        return ImageLoader.Builder(this)
            .okHttpClient(okHttpClient)
            .build()
    }

    companion object {
        private const val USER_AGENT_HEADER = "User-Agent"
        private const val USER_AGENT_VALUE =
            "practicum-android-diploma/1.0 (+https://github.com/mikelog/practicum-android-diploma)"
    }

}
