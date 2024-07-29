package com.target.targetcasestudy.ui.home

import com.target.targetcasestudy.ui.base.Reducer
import javax.annotation.concurrent.Immutable

@Immutable
sealed class HomeEvent : Reducer.ViewEvent {
    data object RetrieveDeals: HomeEvent()
}