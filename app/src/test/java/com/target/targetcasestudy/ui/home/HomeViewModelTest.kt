package com.target.targetcasestudy.ui.home

import app.cash.turbine.test
import com.target.targetcasestudy.BaseTest
import com.target.targetcasestudy.TestMocks
import com.target.targetcasestudy.api.ApiResult
import com.target.targetcasestudy.data.Products
import com.target.targetcasestudy.repo.DealRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HomeViewModelTest: BaseTest() {

    private lateinit var homeViewModel: HomeViewModel

    @MockK
    lateinit var dealRepository: DealRepository

    override fun setup() {
        super.setup()
        homeViewModel = HomeViewModel(dealRepository, testDispatcher)
    }

    @Test
    fun `Test Success ApiResult for ViewEvent - HomeEvent RetrieveDeals`() = runTest {
        val testEvent = HomeEvent.RetrieveDeals
        val deal = TestMocks.provideMockDeal()
        val products = Products(deals = listOf(deal))
        val mockApiResult = ApiResult.Success(products)
        val expectedResult = HomeUiState(deals = listOf(deal))

        coEvery { dealRepository.retrieveDeals() } returns flowOf(mockApiResult)

        homeViewModel.sendEvent(testEvent)
        assertEquals(expectedResult, homeViewModel.viewState.value)
        coVerify(atLeast = 1) { dealRepository.retrieveDeals() }
    }

    @Test
    fun `Test Success ApiResult with null products for ViewEvent - HomeEvent RetrieveDeals`() = runTest {
        val testEvent = HomeEvent.RetrieveDeals
        val mockApiResult = ApiResult.Success<Products>(null)
        val expectedResult = HomeUiState(deals = listOf())

        coEvery { dealRepository.retrieveDeals() } returns flowOf(mockApiResult)

        homeViewModel.sendEvent(testEvent)
        assertEquals(expectedResult, homeViewModel.viewState.value)
        homeViewModel.viewEffects.test {
            val expectedError = "No data present!"
            assertEquals(HomeEffect.Error(expectedError), awaitItem())
        }
        coVerify(atLeast = 1) { dealRepository.retrieveDeals() }
    }

    @Test
    fun `Test Success ApiResult with empty list for ViewEvent - HomeEvent RetrieveDeals`() = runTest {
        val testEvent = HomeEvent.RetrieveDeals
        val products = Products(deals = listOf())
        val mockApiResult = ApiResult.Success(products)
        val expectedResult = HomeUiState(deals = listOf())

        coEvery { dealRepository.retrieveDeals() } returns flowOf(mockApiResult)

        homeViewModel.sendEvent(testEvent)
        assertEquals(expectedResult, homeViewModel.viewState.value)
        homeViewModel.viewEffects.test {
            val expectedError = "No data present!"
            assertEquals(HomeEffect.Error(expectedError), awaitItem())
        }
        coVerify(atLeast = 1) { dealRepository.retrieveDeals() }
    }

    @Test
    fun `Test Success ApiResult with no sale price for ViewEvent - HomeEvent RetrieveDeals`() = runTest {
        val testEvent = HomeEvent.RetrieveDeals
        val deal = TestMocks.provideMockDealWithoutSalePrice()
        val products = Products(deals = listOf(deal))
        val mockApiResult = ApiResult.Success(products)
        val expectedResult = HomeUiState(deals = listOf())

        coEvery { dealRepository.retrieveDeals() } returns flowOf(mockApiResult)

        homeViewModel.sendEvent(testEvent)
        assertEquals(expectedResult, homeViewModel.viewState.value)
        coVerify(atLeast = 1) { dealRepository.retrieveDeals() }
    }

    @Test
    fun `Test Loading ApiResult for ViewEvent - HomeEvent RetrieveDeals`() = runTest {
        val testEvent = HomeEvent.RetrieveDeals
        val mockApiResult = ApiResult.Loading<Products>()

        coEvery { dealRepository.retrieveDeals() } returns flowOf(mockApiResult)
        homeViewModel.sendEvent(testEvent)
        homeViewModel.viewEffects.test {
            assertEquals(HomeEffect.Loading, awaitItem())
        }
    }

    @Test
    fun `Test Error ApiResult for ViewEvent - HomeEvent RetrieveDeals`() = runTest {
        val testEvent = HomeEvent.RetrieveDeals
        val errorMsg = "Api Failure!"
        val mockApiResult = ApiResult.Error<Products>(errorMsg)
        val expectedResult = HomeEffect.Error(errorMsg)

        coEvery { dealRepository.retrieveDeals() } returns flowOf(mockApiResult)
        homeViewModel.sendEvent(testEvent)
        homeViewModel.viewEffects.test {
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `Test Error ApiResult with null error message for ViewEvent - HomeEvent RetrieveDeals`() = runTest {
        val testEvent = HomeEvent.RetrieveDeals
        val mockApiResult = ApiResult.Error<Products>(null)
        val expectedResult = HomeEffect.Error("")

        coEvery { dealRepository.retrieveDeals() } returns flowOf(mockApiResult)
        homeViewModel.sendEvent(testEvent)
        homeViewModel.viewEffects.test {
            assertEquals(expectedResult, awaitItem())
        }
    }

}