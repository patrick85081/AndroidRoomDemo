package com.example.androidroomdemo.database

import android.arch.persistence.room.*

@Dao
interface UserDao
{
    @Query("SELECT * FROM Users")
    fun getAll(): List<User>;

    @Query("SELECT * FROM Users WHERE uid IN (:userIds)")
    fun loadAllByIds(vararg userIds: Int): List<User>;

    @Query("SELECT * FROM Users " +
            "WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User;

    @Insert
    fun insertAll(vararg users: User);

    @Delete
    fun delete(user: User);
}

