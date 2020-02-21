package com.the3rdwheel.breeze.authentication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.the3rdwheel.breeze.authentication.response.AuthResponse

@Database(entities = [AuthResponse::class], version = 1)
abstract class AuthDatabase : RoomDatabase() {


    abstract fun authResponseDao(): AuthResponseDao

    companion object {
        @Volatile
        private var instance: AuthDatabase? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also { instance }
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AuthDatabase::class.java, "auth.db").build()

    }
}