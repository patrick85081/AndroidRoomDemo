package com.example.androidroomdemo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun userDao(): UserDao;


    companion object
    {
        private var instance: AppDatabase? = null;

        /**
         * Kotlin 的 Singleton
         */
        fun getInstance(context: Context): AppDatabase
        {
            if (instance == null)
            {
                synchronized(AppDatabase::class) {
                    if (instance == null)
                    {
                        instance = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "User.db")
                                .allowMainThreadQueries() //can use main thread
                                .build();
                    }
                }
            }
            return instance!!
        }
    }
}