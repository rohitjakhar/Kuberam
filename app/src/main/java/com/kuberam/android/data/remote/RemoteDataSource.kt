package com.kuberam.android.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.kuberam.android.data.DataStorePreferenceStorage
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.data.model.FeedbackModel
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.utils.Constant
import com.kuberam.android.utils.Constant.EXPENSE_DATA
import com.kuberam.android.utils.Constant.TRANSACTION_COLLECTION
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class RemoteDataSource @Inject constructor(
    private val dataStorePreferenceStorage: DataStorePreferenceStorage,
    @Named("userCollectionReference") private val userCollectionReference: CollectionReference,
    @Named("feedbackCollectionReference") private val feedbackCollectionReference: CollectionReference
) {

    suspend fun addTransaction(
        transactionDetailsModel: TransactionDetailsModel,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            val userid = dataStorePreferenceStorage.userProfileData.first().userId
            userCollectionReference.document(userid).collection(TRANSACTION_COLLECTION)
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
            userCollectionReference.document(userid).collection(Constant.INCOME_DATA)
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
            userCollectionReference.document(userid).collection(EXPENSE_DATA)
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
            userCollectionReference.document(userid)
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
            userCollectionReference.document(userid)
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
            userCollectionReference.document(userid).collection(categoryDataModel.transactionType)
                .document(categoryDataModel.categoryName).set(categoryDataModel).await()
            successListener.invoke("Created")
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
            userCollectionReference.document(userid).collection(TRANSACTION_COLLECTION)
                .document(transactionDetailsModel.transactionId).delete().await()
            successListener.invoke("Deleted")
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }

    suspend fun addFeedback(
        feedbackModel: FeedbackModel,
        successListener: (String) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        try {
            feedbackCollectionReference.document().set(feedbackModel).await()
            successListener.invoke("Added")
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }
}
