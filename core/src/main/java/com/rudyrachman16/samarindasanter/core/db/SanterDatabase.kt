package com.rudyrachman16.samarindasanter.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rudyrachman16.samarindasanter.core.model.User

@Database(entities = [User::class], version = 1)
abstract class SanterDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
}