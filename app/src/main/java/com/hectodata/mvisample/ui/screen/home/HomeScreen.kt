package com.hectodata.mvisample.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hectodata.mvisample.base.SIDE_EFFECTS_KEY
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    navController: NavHostController,
    state: HomeContract.HomeState,
    effect: Flow<HomeContract.HomeEffect>,
    event: (event: HomeContract.HomeEvent) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effect.collect {
            when(it) {
                is HomeContract.HomeEffect.Api -> {

                }

                is HomeContract.HomeEffect.Navigate -> {
                    navController.navigate(it.route)
                }

                is HomeContract.HomeEffect.Toast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Hello MVI! Click Count (${state.count})")

            Button(
                modifier = Modifier.padding(top = 20.dp),
                onClick = {
                    event(HomeContract.HomeEvent.OnClickCount)
                }
            ) {
                Text(text = "Count Button")
            }

            Button(
                modifier = Modifier.padding(top = 20.dp),
                onClick = {
                    event(HomeContract.HomeEvent.OnClickIsVisibleButton)
                }
            ) {
                Text(text = "Visible Button")
            }

            if (state.isVisible) {
                Button(
                    modifier = Modifier.padding(top = 20.dp),
                    onClick = {
                        event(HomeContract.HomeEvent.OnClickNavigateSecondScreen)
                    }
                ) {
                    Text(text = "Navigate Second Screen")
                }
            }
        }
    }
}