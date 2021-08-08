package com.kuberam.android.navigation

sealed class Screen(val route: String, val resourceId: String) {
    object DashboardScreen : Screen("nav_dashboard", "Home")
    object Login : Screen("nam_login", "Login")
    object Profile : Screen ("nav_profile", "Profile")
}
