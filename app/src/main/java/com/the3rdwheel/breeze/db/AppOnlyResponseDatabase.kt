package com.the3rdwheel.breeze.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.the3rdwheel.breeze.model.AppOnlyResponse

@Database(entities = [AppOnlyResponse::class], version = 1)
abstract class AppOnlyResponseDatabase : RoomDatabase() {

    abstract fun appOnlyResponseDao(): AppOnlyResponseDao


    companion object {
        @Volatile
        private var instance: AppOnlyResponseDatabase? = null
        private val LOCK = Any()

     operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
         instance ?: buildDatabase(context).also { instance = it }
     }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppOnlyResponseDatabase::class.java, "apponly.db")
                .fallbackToDestructiveMigration().build()



    }
}
