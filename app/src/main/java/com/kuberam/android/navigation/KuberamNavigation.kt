package com.kuberam.android.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kuberam.android.ui.screen.AuthScreen
import com.kuberam.android.ui.screen.DashboardScreen

@ExperimentalMaterialApi
@Composable
fun KuberamNavigation(isLogin: Boolean) {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = if (isLogin) Screen.DashboardScreen.route else Screen.Login.route,
        route = "home"
    ) {
        // TODO: Define all possible routes here
        composable("nav_add") {
        }
        composable("nav_login") {
            AuthScreen(navController)
        }
        composable("nav_profile") {
        }
        composable("nav_dashboard") {
            DashboardScreen(navController)
        }
    }
}
