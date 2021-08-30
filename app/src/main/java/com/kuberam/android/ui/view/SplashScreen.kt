package com.kuberam.android.ui.view

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.kuberam.android.R
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.bioMetricsPrompts
import kotlinx.coroutines.delay

const val UPDATE_REQUEST_CODE = 524

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val isLogin by viewModel.isLogin
    val isLockEnable by viewModel.appLock
    val isFirstTime by viewModel.firstTime
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val context = LocalContext.current
    val resultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode != RESULT_OK) {
                if (isFirstTime) {
                    viewModel.changeFirstTime()
                    navController.navigate(Screen.OnBoardScreen.route)
                } else {
                    if (isLogin) {
                        if (isLockEnable) {
                            bioMetricsPrompts(
                                context.applicationContext as AppCompatActivity,
                                navController
                            )
                        } else {
                            navController.navigate(Screen.DashboardScreen.route) {
                                popUpTo(Screen.DashboardScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    } else {
                        navController.navigate(Screen.Auth.route) {
                            popUpTo(Screen.Auth.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }

    val appUpdateManager: AppUpdateManager =
        AppUpdateManagerFactory.create(context.applicationContext)

    LaunchedEffect(key1 = true) {
        viewModel.checkLogin()
        viewModel.firstTime()
        viewModel.checkAppLock()
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(1500L)
        val appUpdateInfo = appUpdateManager.appUpdateInfo
        appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                appUpdateManager.startUpdateFlowForResult(
                    it,
                    context as Activity,
                    AppUpdateOptions.defaultOptions(AppUpdateType.IMMEDIATE),
                    UPDATE_REQUEST_CODE
                )
                resultLauncher.launch(context.intent)
            } else {
                if (isFirstTime) {
                    viewModel.changeFirstTime()
                    navController.navigate(Screen.OnBoardScreen.route)
                } else {
                    if (isLogin) {
                        if (isLockEnable) {
                            bioMetricsPrompts(context as AppCompatActivity, navController)
                        } else {
                            navController.navigate(Screen.DashboardScreen.route) {
                                popUpTo(Screen.DashboardScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    } else {
                        navController.navigate(Screen.Auth.route) {
                            popUpTo(Screen.Auth.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_kuberam_transparent),
            contentDescription = "logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}
