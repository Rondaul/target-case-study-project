package com.target.targetcasestudy.ui.details

import androidx.compose.runtime.Immutable
import com.target.targetcasestudy.ui.base.Reducer

@Immutable
sealed class DetailsEffect: Reducer.ViewEffect {
    data object Loading : DetailsEffect()
    data class Error(val errorMsg: String) : DetailsEffect()
    data object Success : DetailsEffect()
}