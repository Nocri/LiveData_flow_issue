package com.example.livedataflow

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*

class EmittingLiveData : LiveData<StateData<Int>>() {

    init {
        value = StateData()
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in StateData<Int>>) {
        startEmitting()
        super.observe(owner, observer)
    }

    override fun observeForever(observer: Observer<in StateData<Int>>) {
        startEmitting()
        super.observeForever(observer)
    }

    private fun startEmitting() {
        GlobalScope.launch(Dispatchers.Unconfined) {
            value = value?.loading()

            async {
                while (this.isActive) {
                    delay(1000)
                    postValue(value?.success(0))    //Might need set value
                }
            }.start()
        }
    }

}