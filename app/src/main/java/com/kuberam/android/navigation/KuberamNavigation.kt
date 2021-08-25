package com.kuberam.android.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kuberam.android.ui.view.AuthScreen
import com.kuberam.android.ui.view.DashboardScreen
import com.kuberam.android.ui.view.OnBoardScreen
import com.kuberam.android.ui.view.ProfileScreen
import com.kuberam.android.ui.view.SplashScreen
import com.kuberam.android.ui.view.TransactionDetails
import com.kuberam.android.ui.viewmodel.MainViewModel

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun KuberamNavigation(viewModel: MainViewModel) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.DashboardScreen.route
    ) {
        composable(Screen.Login.route) {
            AuthScreen(navController, viewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController, viewModel)
        }
        composable(
            Screen.DashboardScreen.route,
            enterTransition = { initial, target ->
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(300)
                ) +
                    fadeIn(animationSpec = tween(300))
            }
        ) {
            DashboardScreen(navController, viewModel)
        }
        composable(Screen.TransactionDetails.route) {
            TransactionDetails()
        }
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController, viewModel)
        }
        composable(Screen.OnBoardScreen.route) {
            OnBoardScreen(navController)
        }
    }
}
