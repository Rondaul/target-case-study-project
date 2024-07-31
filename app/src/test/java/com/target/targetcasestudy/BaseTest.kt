package com.target.targetcasestudy

import android.app.Application
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

/**
 * Base Test class for all test classes
 */
open class BaseTest {

    @MockK
    lateinit var application: Application

    private val testCoroutineScheduler = TestCoroutineScheduler()

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher(testCoroutineScheduler)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    open fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    open fun tearDown() {
        testDispatcher.cancel()
        Dispatchers.resetMain()
    }
}