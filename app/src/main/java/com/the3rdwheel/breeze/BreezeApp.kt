package com.the3rdwheel.breeze

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class BreezeApp : Application() {


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        val fontRequest = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Noto Color Emoji Compat",
            R.array.com_google_android_gms_fonts_certs
        )


        val config = FontRequestEmojiCompatConfig(this, fontRequest)
        EmojiCompat.init(config)


        startKoin {
            androidContext(this@BreezeApp)
            modules(com.the3rdwheel.breeze.koin.modules)
        }


    }

}