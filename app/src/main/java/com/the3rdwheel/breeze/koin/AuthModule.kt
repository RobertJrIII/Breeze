package com.the3rdwheel.breeze.koin

import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.reddit.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.reddit.authentication.network.ConnectivityInterceptor
import org.koin.android.ext.koin.androidContext

import org.koin.dsl.module

val authModules = module {

    single { ConnectivityInterceptor(androidContext()) }

    single { Auth.invoke(get()) }
    single { AccountDatabase(androidContext()) }



}

