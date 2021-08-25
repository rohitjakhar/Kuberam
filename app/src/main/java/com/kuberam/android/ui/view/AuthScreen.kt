package com.kuberam.android.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.auth0.android.Auth0
import com.kuberam.android.R
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.NetworkResponse

@Composable
fun AuthScreen(navController: NavController, viewModel: MainViewModel) {
    val auth = Auth0(
        "n1t1L4rqRSFnrnoYrnAEQhHg9svWCcqu",
        "rohitjakhar.us.auth0.com"
    )

    val context = LocalContext.current
    Surface(
        color = Color.Gray,
        modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(bottom = 50.dp),
        shape = RoundedCornerShape(60.dp).copy(topEnd = ZeroCornerSize, topStart = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(R.drawable.ic_sign_in),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(400.dp)
            )
            Button(
                onClick = {
                    viewModel.loginuser(
                        context,
                        auth0 = auth
                    )
                    when (viewModel.loginState.value) {
                        is NetworkResponse.Success -> {
                            Log.d("test343", "Success")
                            navController.navigate(Screen.DashboardScreen.route)
                        }
                        is NetworkResponse.Failure -> {
                            Log.d("test343", "Failed: ${viewModel.loginState.value.message}")
                        }
                        is NetworkResponse.Loading -> {
                            Log.d("test343", "Loading")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(50.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Continue With Auth0")
            }
        }
    }
}
