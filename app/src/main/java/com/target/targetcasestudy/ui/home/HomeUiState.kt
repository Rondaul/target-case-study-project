package com.target.targetcasestudy.ui.home

import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.ui.base.Reducer
import javax.annotation.concurrent.Immutable

@Immutable
data class HomeUiState(
    val deals: List<Deal> = listOf()
) : Reducer.ViewState {
    companion object {
        fun initial() = HomeUiState()
    }
}