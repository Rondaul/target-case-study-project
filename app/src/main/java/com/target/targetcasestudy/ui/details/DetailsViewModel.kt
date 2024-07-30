package com.target.targetcasestudy.ui.details

import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.api.ApiResult
import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.repo.DealRepository
import com.target.targetcasestudy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val dealRepository: DealRepository,
    dispatcher: CoroutineDispatcher
): BaseViewModel<DetailsUiState, DetailsEvent, DetailsEffect>(DetailsUiState.initial(), dispatcher) {
    override fun processEvent(event: DetailsEvent) = when(event) {
        is DetailsEvent.RetrieveDeal -> retrieveDeal(event.dealId)
    }

    private fun retrieveDeal(dealId: Int) {
        viewModelScope.launch(dispatcher) {
            sendEffect(DetailsEffect.Loading)
            val result = dealRepository.retrieveDeal(dealId)
            result.collect { apiResult ->
                val (newState, effect) = reduce(viewState.value, apiResult)
                updateViewState(newState)
                if (effect != null) {
                    sendEffect(effect)
                }
            }
        }
    }

    private fun reduce(oldViewState: DetailsUiState, apiResult: ApiResult<Deal>): Pair<DetailsUiState, DetailsEffect?> {
        return when(apiResult) {
            is ApiResult.Success<Deal> -> {
                val newState = oldViewState.copy(
                    deal = apiResult.data
                )
                Pair(newState, DetailsEffect.Success)
            }
            is ApiResult.Loading -> {
                val effect = DetailsEffect.Loading
                Pair(oldViewState, effect)
            }
            is ApiResult.Error -> {
                val effect = DetailsEffect.Error(apiResult.message ?: "")
                Pair(oldViewState, effect)
            }
        }
    }

}