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
import androidx.core.content.getSystemService

fun isOnline(context: Context) : Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (connectivityManager != null){
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (capabilities != null){
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                Log.i("Internet", "TRANSPORT_CELLULAR")
                return true
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                Log.i("Internet", "TRANSPORT_WIFI")
                return true
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                Log.i("Internet", "TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

//val networkRequest = NetworkRequest.Builder()
//    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
//    .build()
//
//val networkCallBack = object : ConnectivityManager.NetworkCallback(){
//    override fun onAvailable(network: Network) {
//        super.onAvailable(network)
//    }
//
//    override fun onLost(network: Network) {
//        super.onLost(network)
//
//        Log.i("TAG network", "Network not available")
//    }
//}

fun isNetworkStatePermissionGranted(context : Context) : Boolean{
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
}
