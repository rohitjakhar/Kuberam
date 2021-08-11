package com.kuberam.android.navigation

sealed class Screen(val route: String, val resourceId: String) {
    object DashboardScreen : Screen("nav_dashboard", "Home")
    object Login : Screen("nav_login", "Login")
    object Profile : Screen("nav_profile", "Profile")
    object AddTransaction : Screen("nav_add_transaction", "Add Transaction")
    object TransactionDetails : Screen("nav_transaction_details", "Deatils")
}
