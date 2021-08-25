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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.kuberam.android.data.DataStorePreferenceStorage
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.data.model.ProfileDataModel
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.utils.Constant.EXPENSE_DATA
import com.kuberam.android.utils.Constant.INCOME_DATA
import com.kuberam.android.utils.Constant.TRANSACTION_COLLECTION
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val dataStorePreferenceStorage: DataStorePreferenceStorage,
    private val collectionReference: CollectionReference
) {
    suspend fun getUserProfile(
        successListener: (ProfileDataModel) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            val task =
                collectionReference.document(userid).get().await()
            successListener.invoke(task.toObject(ProfileDataModel::class.java)!!)
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    suspend fun getAllTransaction(
        successListener: (List<TransactionDetailsModel>) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        val userid = dataStorePreferenceStorage.userProfileData.first().userId
        val task =
            collectionReference.document(userid).collection(TRANSACTION_COLLECTION)
                .orderBy("transactionDate", Query.Direction.ASCENDING).get().await()
        if (task.isEmpty) {
            failureListener.invoke(Exception("Empty List"))
        } else {
            val transactionDetailsModel: MutableList<TransactionDetailsModel> = mutableListOf()
            for (transaction in task.documents) {
                transactionDetailsModel.add(transaction.toObject(TransactionDetailsModel::class.java)!!)
            }
            successListener.invoke(transactionDetailsModel)
        }
    }

    suspend fun getIncomeData(
        successListener: (List<CategoryDataModel>) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val transactionListModel: MutableList<CategoryDataModel> = mutableListOf()
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            val task = collectionReference.document(userid).collection(INCOME_DATA).get().await()
            if (task.isEmpty) {
                failureListener.invoke(Exception("Empty List"))
            } else {
                for (transaction in task.documents) {
                    transactionListModel.add(transaction.toObject(CategoryDataModel::class.java)!!)
                }
                successListener.invoke(transactionListModel)
            }
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    suspend fun getExpenseData(
        successListener: (List<CategoryDataModel>) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val transactionListModel: MutableList<CategoryDataModel> = mutableListOf()
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            val task = collectionReference.document(userid).collection(EXPENSE_DATA).get().await()
            if (task.isEmpty) {
                failureListener.invoke(Exception("Empty List"))
            } else {
                for (transaction in task.documents) {
                    transactionListModel.add(transaction.toObject(CategoryDataModel::class.java)!!)
                }
                successListener.invoke(transactionListModel)
            }
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    suspend fun addTransaction(
        transactionDetailsModel: TransactionDetailsModel,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            collectionReference.document(userid).collection(TRANSACTION_COLLECTION)
                .document(transactionDetailsModel.transactionId)
                .set(transactionDetailsModel).await()
            successListener.invoke("Added")
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    suspend fun addIncomeData(
        categoryDataModel: CategoryDataModel,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            collectionReference.document(userid).collection(INCOME_DATA)
                .document(categoryDataModel.categoryName)
                .update(
                    "amount",
                    FieldValue.increment(categoryDataModel.amount)
                ).await()
            successListener.invoke("Added")
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    suspend fun addExpenseData(
        categoryDataModel: CategoryDataModel,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            collectionReference.document(userid).collection(EXPENSE_DATA)
                .document(categoryDataModel.categoryName)
                .update(
                    "amount",
                    FieldValue.increment(categoryDataModel.amount)
                ).await()
            successListener.invoke("Added")
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    suspend fun updateTotalIncomeData(
        incomeAmount: Long,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            collectionReference.document(userid)
                .update(
                    "totalIncome",
                    FieldValue.increment(incomeAmount)
                ).await()
            successListener.invoke("Updated")
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    suspend fun updateTotalExpense(
        expenseAmount: Long,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            collectionReference.document(userid)
                .update(
                    "totalExpense",
                    FieldValue.increment(expenseAmount)
                ).await()
            successListener.invoke("Updated")
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    suspend fun createCategory(
        categoryDataModel: CategoryDataModel,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            collectionReference.document(userid).collection(categoryDataModel.transactionType)
                .document(categoryDataModel.categoryName).set(categoryDataModel).await()
            successListener.invoke("Created")
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
            val id = collectionReference.document(userid).get().await()
            if (id.exists()) {
                try {
                    collectionReference.document(userid).update(
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
                    collectionReference.document(userid).set(profileDataModel).await()
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
        context: Context,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        val auth = Auth0(
            "n1t1L4rqRSFnrnoYrnAEQhHg9svWCcqu",
            "rohitjakhar.us.auth0.com"
        )
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

    suspend fun deleteTransaction(
        transactionDetailsModel: TransactionDetailsModel,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            collectionReference.document(userid).collection(TRANSACTION_COLLECTION)
                .document(transactionDetailsModel.transactionId).delete().await()
            successListener.invoke("Deleted")
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }
}
