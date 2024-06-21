package com.rudyrachman16.samarindasanter.core.model

import androidx.room.Entity

@Entity(tableName = "user")
data class User (
    val username: String,
    val password: String
)