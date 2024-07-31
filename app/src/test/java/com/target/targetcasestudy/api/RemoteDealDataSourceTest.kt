package com.target.targetcasestudy.api

import com.target.targetcasestudy.BaseTest
import com.target.targetcasestudy.TestMocks
import com.target.targetcasestudy.data.Products
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

class RemoteDealDataSourceTest: BaseTest() {

    private lateinit var remoteDealDataSource: RemoteDealDataSource

    @MockK
    lateinit var dealApi: DealApi

    override fun setup() {
        super.setup()
        remoteDealDataSource = RemoteDealDataSource(dealApi)
    }

    @Test
    fun `Test retrieveDeals returns ApiResult of products`() = runTest {
        val deal = TestMocks.provideMockDeal()
        val products = Products(deals = listOf(deal))
        coEvery { dealApi.retrieveDeals() } returns Response.success(products)

        val result = remoteDealDataSource.retrieveDeals()
        assertEquals(products, result.data)
    }

    @Test
    fun `Test retrieveDeals with null responseBody returns error ApiResult`() = runTest {
        val response = Response.success<Products>(null)
        val expectedMessage = "Api call failed Code: ${response.code()}, Message: ${response.message()}"
        coEvery { dealApi.retrieveDeals() } returns response

        val result = remoteDealDataSource.retrieveDeals()
        assertTrue(result is ApiResult.Error)
        assertEquals(expectedMessage, result.message)
    }

    @Test
    fun `Test retrieveDeals returns error ApiResult`() = runTest {
        val expectedResult = TestMocks.provideErrorResponse<Products>()
        val expectedMessage = "Api call failed Code: ${expectedResult.code()}, Message: ${expectedResult.message()}"
        coEvery { dealApi.retrieveDeals() } returns expectedResult

        val result = remoteDealDataSource.retrieveDeals()
        assertTrue(result is ApiResult.Error)
        assertEquals(expectedMessage, result.message)
    }

    @Test
    fun `Test retrieveDeals throws exception returns error ApiResult`() = runTest {
        val expectedResult = TestMocks.provideErrorResponse<Products>()
        val expectedMessage = "Code: ${expectedResult.code()}, Message: ${expectedResult.message()}"
        coEvery { dealApi.retrieveDeals() } throws IllegalArgumentException(expectedMessage)

        val result = remoteDealDataSource.retrieveDeals()
        assertTrue(result is ApiResult.Error)
        assertEquals("Api call failed $expectedMessage", result.message)
    }

    @Test
    fun `Test retrieveDeal with id 1 returns ApiResult of deal`() = runTest {
        val deal = TestMocks.provideMockDeal()
        coEvery { dealApi.retrieveDeal(1) } returns Response.success(deal)

        val result = remoteDealDataSource.retrieveDeal(1)
        assertEquals(deal, result.data)
    }
}