package com.the3rdwheel.breeze

import android.app.Application
import android.widget.Toast
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import at.favre.lib.armadillo.Armadillo
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.reddit.RedditUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
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


        val config = FontRequestEmojiCompatConfig(this, fontRequest)
        EmojiCompat.init(config)


        startKoin {
            androidContext(this@BreezeApp)
            modules(com.the3rdwheel.breeze.koin.authModules)
        }
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)

        val isFirstSetUp = prefs.getBoolean("firstSetUp", true)

        if (isFirstSetUp) {
            setUp()
        }

    }

    private fun setUp() {
        val auth: Auth = get()


        CoroutineScope(IO).launch {

            try {

                val tokenResponse = auth.getAuthResponse(RedditUtils.CREDENTIALS)


                val accessToken = tokenResponse.access_token


                withContext(Main) {
                    val securePrefs = Armadillo.create(
                        getSharedPreferences(
                            RedditUtils.SECURE_PREFS,
                            MODE_PRIVATE
                        )
                    ).encryptionFingerprint(this@BreezeApp).build()



                    securePrefs.edit().putString(RedditUtils.SECRET_KEY, accessToken).apply()


                    val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putBoolean("firstSetUp", false)
                    editor.apply()


                    Toast.makeText(this@BreezeApp, accessToken, Toast.LENGTH_LONG).show()
                }


            } catch (io: IOException) {
                Timber.e(io)
            }


        }
    }

}