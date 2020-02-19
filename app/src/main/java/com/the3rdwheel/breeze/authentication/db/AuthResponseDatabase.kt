package com.the3rdwheel.breeze.authentication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.the3rdwheel.breeze.authentication.AuthResponse

@Database(entities = [AuthResponse::class], version = 1)
abstract class AuthResponseDatabase : RoomDatabase() {

    abstract fun authResponseDao(): AuthResponseDao


    companion object {
        @Volatile private var instance: AuthResponseDatabase? = null
        private val LOCK = Any()

     operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
         instance ?: buildDatabase(context).also { instance = it }
     }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AuthResponseDatabase::class.java, "auth.db")
                .fallbackToDestructiveMigration().build()



    }
}
