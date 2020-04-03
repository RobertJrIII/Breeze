package com.the3rdwheel.breeze.dagger

import android.content.Context
import com.the3rdwheel.breeze.ui.fragments.PostsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(context: Context): Builder
    }

    fun inject(postsFragment: PostsFragment)
}