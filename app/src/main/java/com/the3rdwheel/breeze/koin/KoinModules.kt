package com.the3rdwheel.breeze.koin

import coil.ImageLoader
import coil.util.CoilUtils
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.network.SupportInterceptor
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import com.the3rdwheel.breeze.viewmodel.PostViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val authModules = module {


    single { Auth() }


    single { SupportInterceptor(get(), androidContext()) }
    single { RedditApi(get()) }
}

val viewModelsModule = module {
    viewModel { PostViewModel(get()) }
}


val viewModule = module {

    single {
        ImageLoader(androidContext()) {
            okHttpClient {
                OkHttpClient.Builder().cache(CoilUtils.createDefaultCache(androidContext())).build()
            }
        }
    }

}

