package com.rudyrachman16.samarindasanter.core.db

import androidx.room.Database
import com.rudyrachman16.samarindasanter.core.model.User

@Database(entities = [User::class], version = 1)
abstract class SanterDatabase {
    abstract fun getUserDao(): UserDao
    abstract fun getNewsDao(): NewsDao
}