package com.alviano.fetch.api.android.networking

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkChecker(val connectivityManager: ConnectivityManager) {

    fun performAction(action : () -> Unit) {
        if (hasValidInternetConnection()){
            action()
        }
    }

    private fun hasValidInternetConnection(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

}