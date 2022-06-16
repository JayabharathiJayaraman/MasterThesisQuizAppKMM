package com.example.quizapp.utilites

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ViewModelState<T : Any> {
    val state: StateFlow<T>
    val wrappedState: CFlow<T>
    val currentState: T

    fun updateState(state: T.() -> T): T
}

class ViewModelStateImpl<T : Any>(initialState: T) : ViewModelState<T> {
    private val _state: MutableStateFlow<T> = MutableStateFlow(initialState)
    override val state: StateFlow<T> = _state
    override val wrappedState: CFlow<T> = state.wrap()
    override val currentState: T get() = state.value

    override fun updateState(state: T.() -> T): T {
        val newState = state.invoke(_state.value)
        _state.value = newState
        return newState
    }
}