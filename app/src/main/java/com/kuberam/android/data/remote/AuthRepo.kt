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
import com.kuberam.android.data.local.DataStorePreferenceStorage
import com.kuberam.android.data.model.ProfileDataModel
import com.kuberam.android.utils.Constant.USER_DOES_NOT_EXIST
import com.kuberam.android.utils.NetworkResponse
import com.kuberam.android.utils.mapToUnit
import com.kuberam.android.utils.safeFirebaseCall
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class AuthRepo @Inject constructor(
    private val dataStorePreferenceStorage: DataStorePreferenceStorage,
    @Named("userCollectionReference") private val userCollectionReference: CollectionReference,
) {

    suspend fun getUserProfile(): NetworkResponse<ProfileDataModel> =
        safeFirebaseCall(nullDataMessage = "User does not exist", handleNullChecking = true) {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            userCollectionReference.document(userid).get().await()
                .toObject(ProfileDataModel::class.java)
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

    // return success if user exists else failure with does not exist message
    private suspend fun doesUserExist(userid: String) =
        safeFirebaseCall(nullDataMessage = USER_DOES_NOT_EXIST, handleNullChecking = true) {
            userCollectionReference.document(userid).get().await()
                .toObject(ProfileDataModel::class.java)
        }

    suspend fun addUserToFirebase(
        userid: String,
        profileDataModel: ProfileDataModel,
    ): NetworkResponse<Unit> {
        val doesExist = doesUserExist(userid = userid)
        // this means there was a failure in getting data
        if (doesExist is NetworkResponse.Failure && doesExist.message != USER_DOES_NOT_EXIST) return doesExist.mapToUnit()
        return if (doesExist is NetworkResponse.Success) { // This means user already exists
            updateUserData(userid, profileDataModel)
        } else { // This means user does not exist
            addNewUser(userid, profileDataModel)
        }
    }

    private suspend fun updateUserData(
        userid: String,
        profileDataModel: ProfileDataModel
    ): NetworkResponse<Unit> =
        safeFirebaseCall(successMessage = "Success") {
            userCollectionReference.document(userid).update(
                "name", profileDataModel.name,
                "email", profileDataModel.email,
                "profileUrl", profileDataModel.profileUrl,
                "userId", profileDataModel.userId
            ).await()
        }.mapToUnit()

    private suspend fun addNewUser(
        userid: String,
        profileDataModel: ProfileDataModel
    ): NetworkResponse<Unit> = safeFirebaseCall(successMessage = "Success") {
        userCollectionReference.document(userid).set(profileDataModel).await()
    }.mapToUnit()

    suspend fun logoutUser(
        auth: Auth0,
        context: Context,
        successListener: (String) -> Unit,
        failureListener: (String) -> Unit
    ) {
        val clearData = clearData()
        if (clearData is NetworkResponse.Success) {
            logoutFromAuth0(auth, context, successListener, failureListener)
        } else
            failureListener.invoke(clearData.message.toString())
    }

    private fun logoutFromAuth0(
        auth: Auth0,
        context: Context,
        successListener: (String) -> Unit,
        failureListener: (String) -> Unit
    ) {
        WebAuthProvider.logout(auth)
            .withScheme("demo")
            .start(
                context,
                object : Callback<Void?, AuthenticationException> {
                    override fun onSuccess(result: Void?) {
                        successListener.invoke("Logout")
                    }

                    override fun onFailure(error: AuthenticationException) {
                        failureListener.invoke(error.message.toString())
                    }
                }
            )
    }

    private suspend fun clearData(): NetworkResponse<Unit> {
        return try {
            dataStorePreferenceStorage.clearData()
            NetworkResponse.Success(message = "Cleared", data = Unit)
        } catch (e: Exception) {
            NetworkResponse.Failure(message = e.message.toString())
        }
    }
}
