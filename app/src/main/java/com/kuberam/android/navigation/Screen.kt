package com.kuberam.android.navigation

import android.annotation.SuppressLint

sealed class Screen(val route: String, val resourceId: String) {
    object DashboardScreen : Screen("nav_dashboard", "Home")
    object Login : Screen("nav_login", "Login")
    object Profile : Screen("nav_profile", "Profile")
    object AddTransaction : Screen("nav_add_transaction", "Add Transaction")
    object TransactionDetails : Screen("nav_transaction_details", "Deatils")
    @SuppressLint("CustomSplashScreen")
    object SplashScreen : Screen("nav_splash_screen", "Splash Screen")
    object OnBoardScreen : Screen("nav_on_board_screen", "Splash Screen")
    object TransactionsScreen : Screen("nav_transaction_list", "All Transaction")
    object AboutScreen : Screen("nav_about", "All Transaction")
    object FeedbackScreen : Screen("nav_feedback", "All Transaction")

}
