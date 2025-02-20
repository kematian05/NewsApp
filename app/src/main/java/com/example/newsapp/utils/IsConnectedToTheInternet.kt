package com.example.newsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network


fun isConnectedToTheInternet(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    return capabilities != null
}