package com.kuberam.android.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.kuberam.android.data.DataStorePreferenceStorage
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.utils.Constant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class FetchTransaction @Inject constructor(
    private val dataStorePreferenceStorage: DataStorePreferenceStorage,
    @Named("userCollectionReference") private val userCollectionReference: CollectionReference
) {
    suspend fun getAllTransaction(
        successListener: (List<TransactionDetailsModel>) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        val userid = dataStorePreferenceStorage.userProfileData.first().userId
        val task =
            userCollectionReference.document(userid).collection(Constant.TRANSACTION_COLLECTION)
                .orderBy("transactionDate", Query.Direction.DESCENDING).get().await()
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
            val task =
                userCollectionReference.document(userid).collection(Constant.INCOME_DATA).get()
                    .await()
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
            val task =
                userCollectionReference.document(userid).collection(Constant.EXPENSE_DATA).get()
                    .await()
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
}
