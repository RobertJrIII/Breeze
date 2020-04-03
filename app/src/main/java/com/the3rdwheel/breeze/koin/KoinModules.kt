package com.the3rdwheel.breeze.koin

import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.network.SupportInterceptor
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import org.koin.android.ext.koin.androidContext

import org.koin.dsl.module

val authModules = module {


    single { Auth() }


    single { SupportInterceptor(get(), androidContext()) }
    single { RedditApi(get()) }
}



