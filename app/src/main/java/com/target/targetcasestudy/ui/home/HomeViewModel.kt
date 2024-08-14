package com.target.targetcasestudy.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.api.ApiResult
import com.target.targetcasestudy.data.Products
import com.target.targetcasestudy.repo.DealRepository
import com.target.targetcasestudy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [HomeScreen]
 * @param dealRepository - repository for fetching deals
 * @param dispatcher - [CoroutineDispatcher] to be used when performing api calls
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dealRepository: DealRepository,
    dispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
): BaseViewModel<HomeUiState, HomeEvent, HomeEffect>(HomeUiState.initial(), dispatcher) {

    companion object {
        const val API_CALL_KEY = "1"
    }

    var scrollPosition = mutableStateOf(0)

    private fun setShouldCallApiState(shouldCallApi: Boolean) {
        savedStateHandle[API_CALL_KEY] = shouldCallApi
    }

    private fun getShouldCallApiState(): Boolean {
        return savedStateHandle[API_CALL_KEY] ?: true
    }

    /**
     * Process event from [HomeScreen]
     */
    override fun processEvent(event: HomeEvent) = when (event) {
        is HomeEvent.RetrieveDeals -> retrieveDeals()
        is HomeEvent.CheckLogin -> checkLogin(event.isLoginEnabled)
    }

    private fun checkLogin(loginEnabled: Boolean) {
        sendEffect(HomeEffect.LoginResult(false))
    }

    fun saveScrollPosition(position: Int) {
        scrollPosition.value = position
    }

    /**
     * Method to get the list of deal from [dealRepository]
     */
    private fun retrieveDeals()  {
        val homeUiState = getShouldCallApiState()
        if (!homeUiState) {
            sendEffect(HomeEffect.Success)
            return
        }
        viewModelScope.launch(dispatcher) {
            sendEffect(HomeEffect.Loading)
            val result = dealRepository.retrieveDeals()
            result.collect { apiResult ->
                val (newState, effect) = reduce(viewState.value, apiResult)
                setShouldCallApiState(false)
                updateViewState(newState)
                sendEffect(effect)
            }
        }
    }

    /**
     * Method to reduce the [apiResult] data to Pair of new [HomeUiState] and [HomeEffect]
     * @param oldViewState - old viewState to be updated
     * @param apiResult - result fetch from [dealRepository]
     */
    private fun reduce(oldViewState: HomeUiState, apiResult: ApiResult<Products>): Pair<HomeUiState, HomeEffect> {
        return when(apiResult) {
            is ApiResult.Success<Products> -> {
                val deals = apiResult.data?.deals
                if (!deals.isNullOrEmpty()) {
                    val dealWithSales = deals.filter {
                        it.salePrice != null
                    }
                    val newState = oldViewState.copy(
                        deals = dealWithSales
                    )
                    Pair(newState, HomeEffect.Success)
                } else {
                    val effect = HomeEffect.Error("No data present!")
                    Pair(oldViewState, effect)
                }
            }
            is ApiResult.Loading -> {
                val effect = HomeEffect.Loading
                Pair(oldViewState, effect)
            }
            is ApiResult.Error -> {
                val effect = HomeEffect.Error(apiResult.message ?: "")
                Pair(oldViewState, effect)
            }
        }
    }
}