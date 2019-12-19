package com.example.livedataflow

import androidx.annotation.NonNull
import androidx.annotation.Nullable

class StateData<T> {

    @NonNull
    var status: DataStatus
        private set
    @Nullable
    var data: T?
        private set
    @Nullable
    var error: Throwable?
        private set

    init {
        status = DataStatus.CREATED
        data = null
        error = null
    }

    fun loading(): StateData<T> {
        status = DataStatus.LOADING
        data = null
        error = null
        return this
    }

    fun success(@NonNull data: T): StateData<T> {
        status = DataStatus.SUCCESS
        this.data = data
        error = null
        return this
    }

    fun error(@NonNull error: Throwable?): StateData<T> {
        status = DataStatus.ERROR
        data = null
        this.error = error
        return this
    }

    fun complete(): StateData<T> {
        status = DataStatus.COMPLETE
        return this
    }

    enum class DataStatus {
        CREATED, SUCCESS, ERROR, LOADING, COMPLETE
    }

    override fun toString(): String {
        return status.toString() + super.toString()
    }
}