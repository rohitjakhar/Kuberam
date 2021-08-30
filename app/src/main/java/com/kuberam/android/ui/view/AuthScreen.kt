package com.kuberam.android.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.auth0.android.Auth0
import com.kuberam.android.R
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.NetworkResponse
import com.kuberam.android.utils.buttonBackground
import com.kuberam.android.utils.cardBackground
import com.kuberam.android.utils.textNormalColor

@Composable
fun AuthScreen(navController: NavController, viewModel: MainViewModel) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val auth = Auth0(
        context.resources.getString(R.string.client_id),
        context.resources.getString(R.string.domain),
    )

    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            viewModel.checkTheme()
            value = viewModel.darkTheme.value
        }

    val authComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.auth)
    )
    LaunchedEffect(key1 = viewModel.loginState.value) {
        when (viewModel.loginState.value) {
            is NetworkResponse.Success -> {
                isLoading.value = false
                navController.navigate(Screen.DashboardScreen.route)
            }
            is NetworkResponse.Failure -> {
                isLoading.value = false
            }
            is NetworkResponse.Loading -> {
                isLoading.value = true
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(bottom = 50.dp),
        shape = RoundedCornerShape(60.dp).copy(topEnd = ZeroCornerSize, topStart = ZeroCornerSize),
        elevation = 2.dp,
        color = cardBackground(isDarkTheme.value)
    ) {
        ConstraintLayout {
            val (animation, button) = createRefs()

            LottieAnimation(
                composition = authComposition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.padding(bottom = 50.dp).constrainAs(animation) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top)
                }
            )
            Button(
                onClick = {
                    viewModel.loginUser(
                        context,
                        auth0 = auth
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(60.dp)
                    .constrainAs(button) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                    },
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 16.dp),
                contentPadding = PaddingValues(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonBackground(isDarkTheme.value))
            ) {
                Text(
                    stringResource(R.string.continue_with_auth0),
                    style = MaterialTheme.typography.body1,
                    color = textNormalColor(isDarkTheme.value)
                )
            }
        }
    }
}
