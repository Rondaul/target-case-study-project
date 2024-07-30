package com.target.targetcasestudy.ui.details

import androidx.compose.runtime.Immutable
import com.target.targetcasestudy.ui.base.Reducer

@Immutable
sealed class DetailsEvent: Reducer.ViewEvent {
    data class RetrieveDeal(val dealId: Int): DetailsEvent()
}