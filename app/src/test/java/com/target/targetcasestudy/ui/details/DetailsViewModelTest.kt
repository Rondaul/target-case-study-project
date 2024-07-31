package com.target.targetcasestudy.ui.details

import app.cash.turbine.test
import com.target.targetcasestudy.BaseTest
import com.target.targetcasestudy.TestMocks
import com.target.targetcasestudy.api.ApiResult
import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.repo.DealRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DetailsViewModelTest: BaseTest() {

    private lateinit var detailsViewModel: DetailsViewModel

    @MockK
    lateinit var dealRepository: DealRepository

    override fun setup() {
        super.setup()
        detailsViewModel = DetailsViewModel(dealRepository, testDispatcher)
    }

    @Test
    fun `Test ApiResult Success result for DetailsEvent - RetrieveDeal`() {
        val testEvent = DetailsEvent.RetrieveDeal(1)
        val deal = TestMocks.provideMockDeal()
        val mockApiResult = ApiResult.Success(deal)
        val expectedResult = DetailsUiState(deal)

        coEvery { dealRepository.retrieveDeal(1) } returns flowOf(mockApiResult)

        detailsViewModel.sendEvent(testEvent)
        assertEquals(expectedResult, detailsViewModel.viewState.value)
        coVerify(atLeast = 1) { dealRepository.retrieveDeal(1) }
    }

    @Test
    fun `Test ApiResult Loading result for DetailsEvent - RetrieveDeal`() = runTest {
        val testEvent = DetailsEvent.RetrieveDeal(1)
        val mockApiResult = ApiResult.Loading<Deal>()

        coEvery { dealRepository.retrieveDeal(1) } returns flowOf(mockApiResult)
        detailsViewModel.sendEvent(testEvent)
        detailsViewModel.viewEffects.test {
            assertEquals(DetailsEffect.Loading, awaitItem())
        }
    }

    @Test
    fun `Test ApiResult Error result for DetailsEvent - RetrieveDeal`() = runTest {
        val testEvent = DetailsEvent.RetrieveDeal(1)
        val errorMsg = "Api Failure!"
        val mockApiResult = ApiResult.Error<Deal>(errorMsg)

        coEvery { dealRepository.retrieveDeal(1) } returns flowOf(mockApiResult)
        detailsViewModel.sendEvent(testEvent)
        detailsViewModel.viewEffects.test {
            assertEquals(DetailsEffect.Error(errorMsg), awaitItem())
        }
    }

    @Test
    fun `Test ApiResult Error result with null message for DetailsEvent - RetrieveDeal`() = runTest {
        val testEvent = DetailsEvent.RetrieveDeal(1)
        val mockApiResult = ApiResult.Error<Deal>(null)

        coEvery { dealRepository.retrieveDeal(1) } returns flowOf(mockApiResult)
        detailsViewModel.sendEvent(testEvent)
        detailsViewModel.viewEffects.test {
            assertEquals(DetailsEffect.Error(""), awaitItem())
        }
    }
}