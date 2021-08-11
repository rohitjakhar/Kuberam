package com.kuberam.android.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kuberam.android.ui.view.AddTransaction
import com.kuberam.android.ui.view.AuthScreen
import com.kuberam.android.ui.view.DashboardScreen
import com.kuberam.android.ui.view.ProfileScreen
import com.kuberam.android.ui.view.TransactionDetails
import com.kuberam.android.ui.viewmodel.MainViewModel

@ExperimentalMaterialApi
@Composable
fun KuberamNavigation(isLogin: Boolean, viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Screen.Login.route,
        route = "home"
    ) {
        // TODO: Define all possible routes here
        composable(Screen.Login.route) {
            AuthScreen(navController, viewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(Screen.DashboardScreen.route) {
            DashboardScreen(navController)
        }
        composable(Screen.AddTransaction.route) {
            AddTransaction()
        }
        composable(Screen.TransactionDetails.route) {
            TransactionDetails()
        }
    }
}
