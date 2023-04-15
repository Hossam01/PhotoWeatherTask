package com.example.photoweathertask.utils.validation.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.photoweathertask.utils.platform.Versions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkValidatorImp @Inject constructor(
    @ApplicationContext private val context: Context,
    private val versions: Versions
) : NetworkValidator {
    override fun isConnected(): Boolean {
        val connectivityManager = (
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

        if (versions.isAndroidM()) {
            connectivityManager.isActiveNetworkMetered
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && activeNetInfo.isConnected
        }
    }
}