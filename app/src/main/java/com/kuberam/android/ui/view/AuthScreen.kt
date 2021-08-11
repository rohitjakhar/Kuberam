package com.kuberam.android.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kuberam.android.data.model.ProfileModel
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navController: NavController, viewModel: MainViewModel) {
    val auth = Auth0(
        "n1t1L4rqRSFnrnoYrnAEQhHg9svWCcqu",
        "rohitjakhar.us.auth0.com"
    )
    val context = LocalContext.current
    val scrop = rememberCoroutineScope()
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
                                        .start(object :
                                                Callback<UserProfile, AuthenticationException> {
                                                override fun onFailure(error: AuthenticationException) {
                                                    Log.d("test45", "error: ${error.localizedMessage}")
                                                }

                                                override fun onSuccess(result: UserProfile) {
                                                    getDataFromFirebase(result)
                                                }

                                                private fun getDataFromFirebase(result: UserProfile) {
                                                    val userProfile = ProfileModel(
                                                        name = result.name ?: "",
                                                        email = result.email ?: "",
                                                        profileUrl = result.pictureURL ?: "",
                                                        userId = result.getId() ?: ""
                                                    )
                                                    result.getId()?.let {
                                                        scrop.launch(Dispatchers.IO) {
                                                            Firebase.firestore.collection("Kuberam")
                                                                .document(it)
                                                                .set(userProfile)
                                                                .addOnSuccessListener {
                                                                    viewModel.changeLogin(true)
                                                                    viewModel.saveProfile(userProfile)
                                                                    navController.navigate(Screen.DashboardScreen.route)
                                                                }
                                                                .addOnFailureListener {
                                                                    Log.d(
                                                                        "test76",
                                                                        "Failed adding data error: ${it.localizedMessage}"
                                                                    )
                                                                }
                                                        }
                                                    }
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
