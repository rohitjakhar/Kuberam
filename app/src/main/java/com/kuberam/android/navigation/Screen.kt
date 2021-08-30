package com.kuberam.android.navigation

import android.annotation.SuppressLint

sealed class Screen(val route: String) {
    object DashboardScreen : Screen("nav_dashboard")
    object Auth : Screen("nav_login")
    object Profile : Screen("nav_profile")

    @SuppressLint("CustomSplashScreen")
    object SplashScreen : Screen("nav_splash_screen")
    object OnBoardScreen : Screen("nav_on_board_screen")
    object TransactionsScreen : Screen("nav_transaction_list")
    object AboutScreen : Screen("nav_about")
}
