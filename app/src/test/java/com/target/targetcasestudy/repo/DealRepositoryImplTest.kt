package com.target.targetcasestudy.repo

import app.cash.turbine.test
import com.target.targetcasestudy.BaseTest
import com.target.targetcasestudy.TestMocks
import com.target.targetcasestudy.api.ApiResult
import com.target.targetcasestudy.api.RemoteDealDataSource
import com.target.targetcasestudy.data.Products
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DealRepositoryImplTest: BaseTest() {

    private lateinit var dealRepositoryImpl: DealRepositoryImpl

    @MockK
    lateinit var remoteDealDataSource: RemoteDealDataSource

    override fun setup() {
        super.setup()
        dealRepositoryImpl = DealRepositoryImpl(remoteDealDataSource, testDispatcher)
    }

    @Test
    fun `Test retrieveDeals returns flow of ApiResult of products`() = runTest {
        val deal = TestMocks.provideMockDeal()
        val products = Products(deals = listOf(deal))
        val expectedResult = ApiResult.Success(products)
        coEvery { remoteDealDataSource.retrieveDeals() } returns expectedResult

        val result = dealRepositoryImpl.retrieveDeals()
        result.test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `Test retrieveDeal with id 1 returns flow of ApiResult of deal`() = runTest {
        val deal = TestMocks.provideMockDeal()
        val expectedResult = ApiResult.Success(deal)
        coEvery { remoteDealDataSource.retrieveDeal(1) } returns expectedResult

        val result = dealRepositoryImpl.retrieveDeal(1)
        result.test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }

}