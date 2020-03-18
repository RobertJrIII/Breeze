package com.the3rdwheel.breeze.network

import android.content.Context
import com.isupatches.wisefy.WiseFy

class NetworkUtils {


    fun isOnline(context: Context): Boolean {
        return WiseFy.Brains(context).getSmarts().isDeviceConnectedToMobileOrWifiNetwork()
    }

}