package com.kuberam.android.ui.view

import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
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
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.kuberam.android.R
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val islogin by viewModel.isLogin
    val isFirstTime by viewModel.firstTime
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val context = LocalContext.current as FragmentActivity
    LaunchedEffect(key1 = true) {
        viewModel.checkLogin()
        viewModel.firstTime()
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
        navController.popBackStack()
        if (isFirstTime) {
            viewModel.changeFirstTime()
            navController.navigate(Screen.OnBoardScreen.route)
        } else {
            if (islogin) {
                navController.navigate(Screen.DashboardScreen.route) {
                    popUpTo(Screen.DashboardScreen.route) {
                        inclusive = true
                    }
                }
               // showBioMetrictPromopt(context, navController)
            } else {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Login.route) {
                        inclusive = true
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

private val biometricsIgnoredErrors = listOf(
    BiometricPrompt.ERROR_NEGATIVE_BUTTON,
    BiometricPrompt.ERROR_CANCELED,
    BiometricPrompt.ERROR_USER_CANCELED,
    BiometricPrompt.ERROR_NO_BIOMETRICS
)

private fun showBioMetrictPromopt(fr: FragmentActivity, navController: NavController) {
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Unlcokc")
        .setSubtitle("Use finger")
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .build()

    val buinetricPrompt = BiometricPrompt(
        fr,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(fr, "error: ", Toast.LENGTH_SHORT).show()
                fr.finish()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                fr.finish()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                navController.navigate(Screen.DashboardScreen.route) {
                    popUpTo(Screen.DashboardScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    )

    buinetricPrompt.authenticate(promptInfo)
}
