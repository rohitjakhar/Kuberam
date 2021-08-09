package com.kuberam.android.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile

@Composable
fun AuthScreen(navController: NavController) {
    val auth = Auth0(
        "n1t1L4rqRSFnrnoYrnAEQhHg9svWCcqu",
        "rohitjakhar.us.auth0.com"
    )
    val context = LocalContext.current
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    WebAuthProvider.login(auth)
                        .withScheme("demo")
                        .withScope("openid profile email")
                        .start(
                            context,
                            object : Callback<Credentials, AuthenticationException> {
                                override fun onFailure(error: AuthenticationException) {
                                    Log.d("test45", "error: ${error.localizedMessage}")
                                }

                                override fun onSuccess(result: Credentials) {
                                    loadProfile(result.accessToken)
                                }

                                private fun loadProfile(accessToken: String) {
                                    val clinet = AuthenticationAPIClient(auth)
                                    clinet.userInfo(accessToken)
                                        .start(object : Callback<UserProfile, AuthenticationException>{
                                            override fun onFailure(error: AuthenticationException) {
                                                Log.d("test45", "error: ${error.localizedMessage}" )
                                            }

                                            override fun onSuccess(result: UserProfile) {
                                                Log.d("test45", "profile: \n${result.email} ${result.name} ${result.pictureURL} ${result.isEmailVerified}")
                                            }
                                        })
                                }
                            }
                        )
                }
            ) {
                Text("Get Started")
            }
        }
    }
}
