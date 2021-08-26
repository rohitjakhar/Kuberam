package com.kuberam.android.data.remote

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.google.firebase.firestore.CollectionReference
import com.kuberam.android.data.DataStorePreferenceStorage
import com.kuberam.android.data.model.ProfileDataModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class AuthRepo @Inject constructor(
    private val dataStorePreferenceStorage: DataStorePreferenceStorage,
    @Named("userCollectionReference") private val userCollectionReference: CollectionReference,
) {
    suspend fun getUserProfile(
        successListener: (ProfileDataModel) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            val task =
                userCollectionReference.document(userid).get().await()
            successListener.invoke(task.toObject(ProfileDataModel::class.java)!!)
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    fun loginUser(
        context: Context,
        auth: Auth0,
        successListener: (Credentials) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        WebAuthProvider.login(auth)
            .withScheme("demo")
            .withScope("openid profile email")
            .start(
                context,
                object : Callback<Credentials, AuthenticationException> {
                    override fun onFailure(error: AuthenticationException) {
                        failureListener.invoke(error)
                    }

                    override fun onSuccess(result: Credentials) {
                        successListener.invoke(result)
                    }
                }
            )
    }

    fun loadProfile(
        accessToken: String,
        auth: Auth0,
        successListener: (UserProfile) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        val client = AuthenticationAPIClient(auth)
        client.userInfo(accessToken)
            .start(object :
                    Callback<UserProfile, AuthenticationException> {
                    override fun onFailure(error: AuthenticationException) {
                        failureListener.invoke(error)
                    }

                    override fun onSuccess(result: UserProfile) {
                        successListener.invoke(result)
                    }
                })
    }

    suspend fun addUserToFirebase(
        userid: String,
        profileDataModel: ProfileDataModel,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val id = userCollectionReference.document(userid).get().await()
            if (id.exists()) {
                try {
                    userCollectionReference.document(userid).update(
                        "name", profileDataModel.name,
                        "email", profileDataModel.email,
                        "profileUrl", profileDataModel.profileUrl,
                        "userId", profileDataModel.userId
                    ).await()
                    successListener.invoke("Added")
                } catch (e: Exception) {
                    failureListener.invoke(e)
                }
            } else {
                try {
                    userCollectionReference.document(userid).set(profileDataModel).await()
                    successListener.invoke("Added")
                } catch (e: Exception) {
                    failureListener.invoke(e)
                }
            }
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    suspend fun logoutUser(
        auth: Auth0,
        context: Context,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        clearData(
            successListener = {
                WebAuthProvider.logout(auth)
                    .withScheme("demo")
                    .start(
                        context,
                        object : Callback<Void?, AuthenticationException> {
                            override fun onSuccess(result: Void?) {
                                successListener.invoke("Logout")
                            }

                            override fun onFailure(error: AuthenticationException) {
                                failureListener.invoke(error)
                            }
                        }
                    )
            },
            failureListener = {
                failureListener.invoke(it)
            }
        )
    }

    private suspend fun clearData(
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            dataStorePreferenceStorage.clearData()
            successListener.invoke("Cleared")
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }
}
