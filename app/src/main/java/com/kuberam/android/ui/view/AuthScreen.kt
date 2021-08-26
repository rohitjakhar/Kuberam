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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.auth0.android.Auth0
import com.kuberam.android.R
import com.kuberam.android.component.LoadingComponent
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.NetworkResponse

@Composable
fun AuthScreen(navController: NavController, viewModel: MainViewModel) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val auth = Auth0(
        context.resources.getString(R.string.client_id),
        context.resources.getString(R.string.domain),
    )

    LaunchedEffect(key1 = viewModel.loginState.value) {
        when (viewModel.loginState.value) {
            is NetworkResponse.Success -> {
                Log.d("test4343", "Success")
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
        color = MaterialTheme.colors.onSurface
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            if (isLoading.value){
                LoadingComponent()
            }
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
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(60.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 16.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                Text(
                    "Continue With Auth0",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}
