package com.the3rdwheel.breeze.dagger

import android.content.Context
import coil.ImageLoader
import coil.util.CoilUtils
import com.the3rdwheel.breeze.network.RedditAuthenticator
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAuth() = Auth()


    @Provides
    @Singleton
    fun provideNetworkInterceptor(auth: Auth, context: Context) =
        RedditAuthenticator(auth, context)


    @Provides
    @Singleton
    fun provideRedditApi(interceptor: RedditAuthenticator) =
        RedditApi.invoke(interceptor)

    @Provides
    @Singleton
    fun provideImageLoader(context: Context): ImageLoader {
        return ImageLoader(context) {
            okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(context))
                    .build()
            }
        }
    }


}