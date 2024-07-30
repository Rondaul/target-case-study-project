package com.target.targetcasestudy.ui.home

import com.target.targetcasestudy.ui.base.Reducer
import javax.annotation.concurrent.Immutable

/**
 * Sealed class for [HomeScreen] UI ViewEvent
 */
@Immutable
sealed class HomeEvent : Reducer.ViewEvent {
    data object RetrieveDeals: HomeEvent()
}