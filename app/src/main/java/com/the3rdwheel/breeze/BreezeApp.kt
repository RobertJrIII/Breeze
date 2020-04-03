package com.the3rdwheel.breeze

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.the3rdwheel.breeze.dagger.AppComponent
import com.the3rdwheel.breeze.dagger.DaggerAppComponent
import timber.log.Timber

class BreezeApp : Application() {

    private lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val fontRequest = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Noto Color Emoji Compat",
            R.array.com_google_android_gms_fonts_certs
        )
        val config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
        EmojiCompat.init(config)


        appComponent = DaggerAppComponent.builder().application(this).build()

    }

    fun getAppComponent() = appComponent
}