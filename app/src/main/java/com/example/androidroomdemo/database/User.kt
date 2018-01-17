package com.example.androidroomdemo.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by user on 2018/1/17.
 */
@Entity(tableName = "Users")
data class User(

        @ColumnInfo(name = "first_name") var firstName: String,
        @ColumnInfo(name = "last_name") var lastName: String)
{
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0;
}