package com.example.smartfarming.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.core.content.ContextCompat

val networkRequest = NetworkRequest.Builder()
    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
    .build()

val networkCallBack = object : ConnectivityManager.NetworkCallback(){
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
    }

    override fun onLost(network: Network) {
        super.onLost(network)

        Log.i("TAG network", "Network not available")
    }
}

fun isNetworkStatePermissionGranted(context : Context) : Boolean{
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
}

fun isWriteSettingPermissionGranted(context : Context) : Boolean{
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED
}