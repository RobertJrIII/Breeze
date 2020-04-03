package com.the3rdwheel.breeze.dagger

import android.content.Context
import com.the3rdwheel.breeze.network.SupportInterceptor
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Named("Auth")
    @Singleton
    fun provideAuth() = Auth()


    @Provides
    @Named("Interceptor")
    @Singleton
    fun provideNetworkInterceptor(@Named("Auth") auth: Auth, context: Context) =
        SupportInterceptor(auth, context)


    @Provides
    @Singleton
    @Named("RedditApi")
    fun provideRedditApi(@Named("Interceptor") interceptor: SupportInterceptor) =
        RedditApi.invoke(interceptor)

}