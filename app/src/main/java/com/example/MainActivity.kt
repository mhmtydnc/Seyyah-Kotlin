package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.LiveNavigationScreen
import com.example.ui.screens.PlannerScreen
import com.example.ui.screens.SavedTripsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.RouteExplorerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RotaRehberiApp()
                }
            }
        }
    }
}

@Composable
fun RotaRehberiApp() {
    val navController = rememberNavController()
    val viewModel: RouteExplorerViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "planner"
    ) {
        composable("planner") {
            PlannerScreen(
                viewModel = viewModel,
                onNavigateToLive = {
                    navController.navigate("live_nav")
                },
                onNavigateToSaved = {
                    navController.navigate("saved_trips")
                }
            )
        }

        composable("live_nav") {
            LiveNavigationScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("saved_trips") {
            SavedTripsScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                },
                onLoadTrip = { _, _ ->
                    navController.popBackStack()
                }
            )
        }
    }
}
