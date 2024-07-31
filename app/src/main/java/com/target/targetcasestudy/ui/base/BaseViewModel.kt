package com.target.targetcasestudy.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel for all viewmodel to extend from. Contains base setup for MVI architecture
 */
abstract class BaseViewModel<ViewState: Reducer.ViewState, ViewEvent: Reducer.ViewEvent,
        ViewEffect: Reducer.ViewEffect>(
    initialViewState: ViewState,
    protected val dispatcher: CoroutineDispatcher
) : ViewModel() {

    protected abstract fun processEvent(event: ViewEvent)

    private val _viewState = MutableStateFlow(initialViewState)
    val viewState = _viewState.asStateFlow()

    private val _viewEvents = MutableSharedFlow<ViewEvent>()

    private val _viewEffects = Channel<ViewEffect>(Channel.CONFLATED)
    val viewEffects = _viewEffects.receiveAsFlow()

    init {
        viewModelScope.launch(dispatcher) {
            _viewEvents.collect { action ->
                processEvent(action)
            }
        }
    }

    protected open fun updateViewState(state: ViewState) {
        _viewState.value = state
    }

    fun sendEvent(event: ViewEvent) = viewModelScope.launch(dispatcher) {
        _viewEvents.emit(event)
    }

    protected fun sendEffect(effect: ViewEffect) = viewModelScope.launch(dispatcher) {
        _viewEffects.trySend(effect)
    }

}