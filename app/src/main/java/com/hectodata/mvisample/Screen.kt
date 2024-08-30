package com.hectodata.mvisample

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home_screen")
    data object Second: Screen(route = "second_screen")
}