package com.hectodata.mvisample.ui.screen.home

import com.hectodata.mvisample.base.ViewEvent
import com.hectodata.mvisample.base.ViewEffect
import com.hectodata.mvisample.base.ViewState

class HomeContract {

    sealed class HomeEvent: ViewEvent {
        data object OnClickCount: HomeEvent()
        data object OnClickIsVisibleButton: HomeEvent()
        data object OnClickNavigateSecondScreen: HomeEvent()
    }

    data class HomeState(
        val isVisible: Boolean = false,
        val count: Int = 0,
        val progress: Boolean = false
    ): ViewState

    sealed class HomeEffect: ViewEffect {
        data class Toast(val message: String): HomeEffect()
        data class Api(val api: String, val param: String): HomeEffect()
        data class Navigate(val route: String, val param: String = ""): HomeEffect()
    }

}