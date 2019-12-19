package com.example.livedataflow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LiveDataFlowTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    @ExperimentalCoroutinesApi
    fun flowEmissionIsCorrect() = runBlocking {
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)

        val list = EmittingLiveData().asFlow().onEach { println(it.status) }.take(3).toList()

        assert(
            list.map { it.status } == listOf(
                StateData.DataStatus.LOADING,
                StateData.DataStatus.SUCCESS,
                StateData.DataStatus.SUCCESS
            )
        )
    }
}
