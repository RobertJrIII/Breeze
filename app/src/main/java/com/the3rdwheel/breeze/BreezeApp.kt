package com.the3rdwheel.breeze

import android.app.Application
import android.widget.Toast
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.the3rdwheel.breeze.rawk.authentication.api.Auth
import com.the3rdwheel.breeze.rawk.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.rawk.authentication.db.entity.Account
import com.the3rdwheel.breeze.rawk.RedditUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
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

        val isSetUp = prefs.getBoolean("firstSetUp", true)

        if (isSetUp) {
            setUp()
        }

    }

    private fun setUp() {
        val auth: Auth = get()
        val database: AccountDatabase = get()

        CoroutineScope(IO).launch {
            try {
                val response =
                    auth.getAuthResponse(RedditUtils.CREDENTIALS)
                database.accountDao()
                    .insert(
                        Account(
                            RedditUtils.ANONYMOUS_USER,
                            RedditUtils.ANONYMOUS_KARMA,
                            response,
                            RedditUtils.CURRENT_USER
                        )
                    )

                val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putBoolean("firstSetUp", false)
                editor.apply()


                withContext(Main) {

                    Toast.makeText(this@BreezeApp, "Saved", Toast.LENGTH_LONG).show()
                }


            } catch (e: IOException) {
                Timber.e(e)
            }
        }
    }

}