package com.hectodata.mvisample.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


interface ViewEvent
interface ViewState
interface ViewEffect

const val SIDE_EFFECTS_KEY = "side-effects_key"

abstract class BaseViewModel<Event: ViewEvent, UiState: ViewState, Effect: ViewEffect>: ViewModel() {

    abstract fun initState(): UiState
    abstract fun handleEvents(event: Event)
    abstract fun handleException(throwable: Throwable)

    private val initialState: UiState by lazy { initState() }

    private val _state: MutableState<UiState> = mutableStateOf(initialState)
    val state: State<UiState> = _state

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }



    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = state.value.reducer()
        _state.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }





    open fun launchViewModelScope(dispatcher: CoroutineDispatcher = Dispatchers.IO, doWork: suspend () -> Unit) =
        viewModelScope.launch(viewModelScope.coroutineContext + dispatcher + exceptionHandler) {
            doWork()
        }

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
        handleException(throwable)
    }

}