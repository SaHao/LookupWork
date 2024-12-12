package com.lllloookkk.upupp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

object CommonUtil {
    fun isVpnConnected(context: Context): Int {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: Network? = cm.activeNetwork
        val networkCapabilities = cm.getNetworkCapabilities(activeNetwork)

        return if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true) 0 else 1
    }
}