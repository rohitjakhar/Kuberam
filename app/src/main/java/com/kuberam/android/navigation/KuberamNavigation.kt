package com.kuberam.android.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kuberam.android.ui.view.AboutScreen
import com.kuberam.android.ui.view.AllTransactionScreen
import com.kuberam.android.ui.view.AuthScreen
import com.kuberam.android.ui.view.DashboardScreen
import com.kuberam.android.ui.view.OnBoardScreen
import com.kuberam.android.ui.view.ProfileScreen
import com.kuberam.android.ui.view.SplashScreen
import com.kuberam.android.ui.viewmodel.MainViewModel

@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun KuberamNavigation(viewModel: MainViewModel) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(navController, viewModel)
        }
        composable(
            Screen.Profile.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 600 },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 100 },
                    animationSpec = tween(600)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            ProfileScreen(navController, viewModel)
        }
        composable(
            Screen.DashboardScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 100 },
                    animationSpec = tween(600)
                ) + fadeIn(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 100 },
                    animationSpec = tween(600)
                ) +
                    fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 100 },
                    animationSpec = tween(100)
                ) + fadeOut(animationSpec = tween(600))
            }
        ) {
            DashboardScreen(navController, viewModel)
        }
        composable(
            Screen.SplashScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 100 },
                    animationSpec = tween(600)
                ) + fadeIn(animationSpec = tween(600))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 100 },
                    animationSpec = tween(600)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            SplashScreen(navController, viewModel)
        }
        composable(
            Screen.OnBoardScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 100 },
                    animationSpec = tween(600)
                ) +
                    fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 100 },
                    animationSpec = tween(100)
                ) + fadeOut(animationSpec = tween(600))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 100 },
                    animationSpec = tween(600)
                ) +
                    fadeIn(animationSpec = tween(300))
            }
        ) {
            OnBoardScreen(navController, viewModel)
        }
        composable(
            Screen.TransactionsScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 100 },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 100 },
                    animationSpec = tween(100)
                ) + fadeOut(animationSpec = tween(600))
            }
        ) {
            AllTransactionScreen(viewModel)
        }
        composable(
            Screen.AboutScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 100 },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 100 },
                    animationSpec = tween(100)
                ) + fadeOut(animationSpec = tween(600))
            }
        ) {
            AboutScreen(viewModel)
        }
    }
}
