package com.rudyrachman16.samarindasanter.core.utils

import com.rudyrachman16.samarindasanter.core.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object FlowUtils {
    fun <T> defaultFlowCallback(onSuccess: suspend () -> T) = flow {
        emit(Status.Loading)
        try {
            emit(Status.Success(onSuccess()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Status.Error(e))
        }
    }.cancellable().flowOn(Dispatchers.IO)
}