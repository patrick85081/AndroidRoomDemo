package com.example.androidroomdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.androidroomdemo.database.AppDatabase
import com.example.androidroomdemo.database.User

/**
 * Created by user on 2018/1/17.
 */
class MainActivity : AppCompatActivity()
{
    companion object
    {
        private val TAG = "MainActivity";
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(this);

        if (db.userDao().getAll().isEmpty())
            db.userDao()
                    .insertAll(
                            User("Patrick", "Shih"),
                            User("Mark", "Lui"),
                            User("Joy", "Lin"));

        val allUsers = db.userDao().getAll();

        val patrick = db.userDao().findByName("Patrick", "Shih");

        val theUser = db.userDao().loadAllByIds(1, 2);

        Log.d(TAG, "allUsers count ${allUsers.size}");
    }
}