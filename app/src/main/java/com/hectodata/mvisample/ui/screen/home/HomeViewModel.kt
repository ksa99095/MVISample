package com.hectodata.mvisample.ui.screen.home

import android.net.http.HttpException
import android.os.Build
import android.os.ext.SdkExtensions
import com.hectodata.mvisample.Screen
import com.hectodata.mvisample.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): BaseViewModel<HomeContract.HomeEvent, HomeContract.HomeState, HomeContract.HomeEffect>() {

    override fun initState() = HomeContract.HomeState(
        count = 0, isVisible = false
    )

    override fun handleException(throwable: Throwable) {
        val message = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && SdkExtensions.getExtensionVersion(Build.VERSION_CODES.S) >= 7) {
            when(throwable) {
                is SocketException -> {
                    "네트워크 연결을 확인해 주세요."
                }
                is HttpException -> {
                    "네트워크 연결을 확인해 주세요."
                }
                is UnknownHostException -> {
                    "네트워크 연결을 확인해 주세요."
                }
                is NullPointerException -> {
                    "오류가 발생하였습니다."
                }
                else -> {
                    "오류가 발생하였습니다."
                }
            }
        } else {
            when(throwable) {
                is SocketException -> {
                    "네트워크 연결을 확인해 주세요."
                }
                is UnknownHostException -> {
                    "네트워크 연결을 확인해 주세요."
                }
                is NullPointerException -> {
                    "오류가 발생하였습니다."
                }
                else -> {
                    "오류가 발생하였습니다."
                }
            }
        }

        setEffect { HomeContract.HomeEffect.Toast(message) }
        setState { copy(progress = false) }
    }

    override fun handleEvents(event: HomeContract.HomeEvent) {
        when (event) {
            is HomeContract.HomeEvent.OnClickCount -> {
                if (state.value.count >= 10) {
                    setEffect { HomeContract.HomeEffect.Toast("카운트 증가는 10 까지 가능합니다.") }
                    setState { copy(isVisible = true) }
                } else {
                    setState { copy(count = state.value.count + 1) }
                }
            }

            is HomeContract.HomeEvent.OnClickIsVisibleButton -> {
                setState { copy(isVisible = !state.value.isVisible) }
            }

            is HomeContract.HomeEvent.OnClickNavigateSecondScreen -> {
                setEffect { HomeContract.HomeEffect.Navigate(route = Screen.Second.route) }
            }
        }
    }
}