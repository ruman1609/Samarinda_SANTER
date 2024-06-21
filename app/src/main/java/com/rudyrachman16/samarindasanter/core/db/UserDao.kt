package com.rudyrachman16.samarindasanter.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rudyrachman16.samarindasanter.core.model.User

@Dao
interface UserDao {
    @Insert(entity = User::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun registration(user: User)

    @Query("SELECT * FROM user WHERE username == :username AND password == :password")
    suspend fun login(username: String, password: String): User
}