package com.kuberam.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kuberam.android.ui.screen.DashboardScreen

@Composable
fun KuberamNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Screen.DashboardScreen.route,
        route = "home"
    ) {
        // TODO: Define all possible routes here
        composable("nav_add") {
        }
        composable("nav_auth") {
        }
        composable("nav_profile") {
        }
        composable("nav_dashboard") {
            DashboardScreen(navController)
        }
    }
}
