package com.hectodata.mvisample

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hectodata.mvisample.ui.screen.home.HomeScreen
import com.hectodata.mvisample.ui.screen.home.HomeViewModel
import com.hectodata.mvisample.ui.screen.second.SecondScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {

        composable(
            route = Screen.Home.route
        ) {
            val viewModel: HomeViewModel = viewModel()
            HomeScreen(
                navController = navController,
                state = viewModel.state.value,
                effect = viewModel.effect,
                event = { event -> viewModel.setEvent(event) },
            )
        }

        composable(
            route = Screen.Second.route
        ) {
            SecondScreen(navController)
        }
    }
}