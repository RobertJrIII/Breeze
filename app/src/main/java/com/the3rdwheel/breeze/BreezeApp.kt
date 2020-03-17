package com.the3rdwheel.breeze

import android.app.Application
import android.widget.Toast
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.the3rdwheel.breeze.koin.authModules
import com.the3rdwheel.breeze.koin.viewModule
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.reddit.RedditUtils
import de.adorsys.android.securestoragelibrary.SecurePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class BreezeApp : Application() {


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

        startKoin {
            androidContext(applicationContext)
            modules(listOf(authModules, viewModule))
        }


    }


}