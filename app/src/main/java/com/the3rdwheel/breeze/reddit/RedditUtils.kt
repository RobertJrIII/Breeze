package com.the3rdwheel.breeze.reddit

import okhttp3.Credentials

object RedditUtils {
    private const val CLIENT_ID = "o6QI-Md19zAQqg"
    const val REDDIT_URL = "https://www.reddit.com/"
    const val REDDIT_AUTH_URL = "https://www.reddit.com/api/v1/"
    const val REDDIT_BASE_URL = "https://oauth.reddit.com/"
    const val USER_AGENT = "android:com.the3rdwheel.breeze:0.1 (by /u/RobertJrIII)"
    const val AUTHORIZATION_KEY = "Authorization"
    const val AUTHORIZATION_BASE = "bearer "
    const val SECRET_KEY = "Secret_ANONYMOUS"
    const val SECURE_PREFS = "secret_shared_prefs"

    fun getCredentials(): String {
        return Credentials.basic(CLIENT_ID, "")
    }
}