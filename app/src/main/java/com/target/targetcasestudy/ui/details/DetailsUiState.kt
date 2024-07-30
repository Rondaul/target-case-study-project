package com.target.targetcasestudy.ui.details

import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.ui.base.Reducer

data class DetailsUiState(
    val deal: Deal? = null
): Reducer.ViewState {

    companion object {
        fun initial() = DetailsUiState()
    }
}