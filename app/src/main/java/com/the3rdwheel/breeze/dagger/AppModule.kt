package com.the3rdwheel.breeze.dagger

import android.content.Context
import com.the3rdwheel.breeze.network.BaseHeader
import com.the3rdwheel.breeze.network.RedditAuthenticator
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.reddit.api.RedditApi
import dagger.Module
import dagger.Provides
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
    fun provideBaseHeader() = BaseHeader()

    @Provides
    @Singleton
    fun provideRedditApi(interceptor: RedditAuthenticator, baseHeader: BaseHeader) =
        RedditApi.invoke(interceptor, baseHeader)


}