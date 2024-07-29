package com.target.targetcasestudy.ui.base

sealed interface Reducer {

    interface ViewState: Reducer

    interface ViewEvent: Reducer

    interface ViewEffect: Reducer
}