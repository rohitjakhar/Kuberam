package com.kuberam.android.ui.view

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    viewModel.checkLogin()
    Scaffold {
        val islogin by viewModel.isLogin
        Log.d("test90", "islogin: $islogin")
        if (islogin) {
            navController.navigate(Screen.DashboardScreen.route)
        } else {
            navController.navigate(Screen.Login.route)
        }
    }
}
