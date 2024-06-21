package com.rudyrachman16.samarindasanter.core

import android.content.Context
import androidx.room.Room
import com.rudyrachman16.samarindasanter.core.db.SanterDatabase

object Injection {
    fun injectRepository(context: Context) = Repository(
        Room.databaseBuilder(context, SanterDatabase::class.java, "SanterDatabase")
            .fallbackToDestructiveMigration().build().getUserDao()
    )
}