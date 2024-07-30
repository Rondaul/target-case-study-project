package com.target.targetcasestudy.ui.home

import com.target.targetcasestudy.ui.base.Reducer
import javax.annotation.concurrent.Immutable

/**
 * Sealed class for [HomeScreen] UI ViewEffect
 */
@Immutable
sealed class HomeEffect : Reducer.ViewEffect {
    data object Loading : HomeEffect()
    data class Error(val errorMsg: String) : HomeEffect()
    data object Success : HomeEffect()
}