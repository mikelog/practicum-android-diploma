package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

interface ConnectivityChecker {
    fun isNetworkAvailable(): Boolean
}

class ConnectivityCheckerImpl(
    private val context: Context,
) : ConnectivityChecker {

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasActiveNetwork(connectivityManager)
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

    private fun hasActiveNetwork(
        connectivityManager: ConnectivityManager,
    ): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = network?.let(connectivityManager::getNetworkCapabilities)

        return when {
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> true
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> true
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> true
            else -> false
        }
    }
}
