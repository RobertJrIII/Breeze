package com.the3rdwheel.breeze.network

import android.content.Context
import com.isupatches.wisefy.WiseFy
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

class ConnectivityInterceptor(context: Context) : Interceptor {
    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
//        if (!isOnline())
//            throw NoNetworkException()

        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        return WiseFy.Brains(appContext).getSmarts().isDeviceConnectedToMobileOrWifiNetwork()
    }

    class NoNetworkException : IOException()

}