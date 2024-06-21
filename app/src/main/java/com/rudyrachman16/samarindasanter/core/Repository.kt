package com.rudyrachman16.samarindasanter.core

import com.rudyrachman16.samarindasanter.core.db.SanterDatabase
import com.rudyrachman16.samarindasanter.core.db.UserDao
import com.rudyrachman16.samarindasanter.core.model.User
import kotlinx.coroutines.flow.flow

class Repository(private val userDao: UserDao) {
    fun login(username: String, password: String) = flow<Status<User>> {
        emit(Status.Loading)
        try {
            emit(Status.Success(userDao.login(username, password)))
        } catch (e: Exception) {
            emit(Status.Error(e))
        }
    }
}