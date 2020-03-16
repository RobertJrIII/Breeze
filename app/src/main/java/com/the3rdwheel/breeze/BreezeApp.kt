package com.the3rdwheel.breeze

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import at.favre.lib.armadillo.Armadillo
import com.the3rdwheel.breeze.koin.authModules
import com.the3rdwheel.breeze.koin.viewModule
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.reddit.RedditUtils
import kotlinx.coroutines.CoroutineScope
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


        val config = FontRequestEmojiCompatConfig(this, fontRequest)
        EmojiCompat.init(config)

        startKoin {
            androidContext(this@BreezeApp)
            modules(listOf(authModules, viewModule))
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
                retrieveToken(auth)
            } catch (e: Exception) {
                withContext(Main) {

                    Toast.makeText(
                        this@BreezeApp,
                        "No network detected. Try again later.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Timber.e(e)
            }


        }
    }

    private fun retrieveToken(auth: Auth) = runBlocking {
        val response = auth.getAuthResponse(RedditUtils.CREDENTIALS)


        if (response.isSuccessful && response.body() != null) {
            val accessToken = response.body()!!.access_token
            withContext(Main) {
                val securePrefs = Armadillo.create(
                    getSharedPreferences(
                        RedditUtils.SECURE_PREFS,
                        Context.MODE_PRIVATE
                    )
                ).encryptionFingerprint(this@BreezeApp).build()


                securePrefs.edit().putString(RedditUtils.SECRET_KEY, accessToken).apply()


                val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putBoolean("firstSetUp", false)
                editor.apply()

                Toast.makeText(this@BreezeApp, accessToken, Toast.LENGTH_LONG).show()
            }
        }


    }

}