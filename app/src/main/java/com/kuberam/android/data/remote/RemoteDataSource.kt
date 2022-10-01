package com.kuberam.android.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.kuberam.android.data.local.DataStorePreferenceStorage
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.data.model.FeedbackModel
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.utils.Constant
import com.kuberam.android.utils.Constant.EXPENSE_DATA
import com.kuberam.android.utils.Constant.TRANSACTION_COLLECTION
import com.kuberam.android.utils.safeResultUnit
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
        transactionDetailsModel: TransactionDetailsModel
    ) = safeResultUnit {
        val userid = dataStorePreferenceStorage.userProfileData.first().userId
        userCollectionReference.document(userid).collection(TRANSACTION_COLLECTION)
            .document(transactionDetailsModel.transactionId)
            .set(transactionDetailsModel).await()
    }

    suspend fun addIncomeData(
        categoryDataModel: CategoryDataModel
    ) = safeResultUnit {
        val userid = dataStorePreferenceStorage.userProfileData.first().userId
        userCollectionReference.document(userid).collection(Constant.INCOME_DATA)
            .document(categoryDataModel.categoryName)
            .update(
                "amount",
                FieldValue.increment(categoryDataModel.amount)
            ).await()
    }

    suspend fun addExpenseData(
        categoryDataModel: CategoryDataModel
    ) = safeResultUnit {
        val userid = dataStorePreferenceStorage.userProfileData.first().userId
        userCollectionReference.document(userid).collection(EXPENSE_DATA)
            .document(categoryDataModel.categoryName)
            .update(
                "amount",
                FieldValue.increment(categoryDataModel.amount)
            ).await()
    }

    suspend fun updateTotalIncomeData(
        incomeAmount: Long
    ) = safeResultUnit {
        val userid = dataStorePreferenceStorage.userProfileData.first().userId
        userCollectionReference.document(userid)
            .update(
                "totalIncome",
                FieldValue.increment(incomeAmount)
            ).await()
    }

    suspend fun updateTotalExpense(
        expenseAmount: Long
    ) = safeResultUnit {
        val userid = dataStorePreferenceStorage.userProfileData.first().userId
        userCollectionReference.document(userid)
            .update(
                "totalExpense",
                FieldValue.increment(expenseAmount)
            ).await()
    }

    suspend fun createCategory(
        categoryDataModel: CategoryDataModel
    ) = safeResultUnit {
        val userid = dataStorePreferenceStorage.userProfileData.first().userId
        userCollectionReference.document(userid).collection(categoryDataModel.transactionType)
            .document(categoryDataModel.categoryName).set(categoryDataModel).await()
    }

    suspend fun deleteTransaction(
        transactionDetailsModel: TransactionDetailsModel
    ) = safeResultUnit {
        val userid = dataStorePreferenceStorage.userProfileData.first().userId
        userCollectionReference.document(userid).collection(TRANSACTION_COLLECTION)
            .document(transactionDetailsModel.transactionId).delete().await()
    }

    suspend fun addFeedback(
        feedbackText: String
    ) = safeResultUnit {
        val feedbackModel = FeedbackModel(
            userId = dataStorePreferenceStorage.userProfileData.first().userId,
            userName = dataStorePreferenceStorage.userProfileData.first().name,
            feedback = feedbackText
        )
        feedbackCollectionReference.document().set(feedbackModel).await()
    }
}
