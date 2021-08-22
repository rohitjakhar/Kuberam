package com.kuberam.android.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.kuberam.android.data.DataStorePreferenceStorage
import com.kuberam.android.data.model.CategoryDataModel
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
    suspend fun getAllTransaction(
        successListener: (List<TransactionDetailsModel>) -> Unit,
        failureListener: (Exception) -> Unit
    ) {
        val userid = dataStorePreferenceStorage.userProfile.first().userId
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
            val userid = dataStorePreferenceStorage.userProfile.first().userId
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
            val userid = dataStorePreferenceStorage.userProfile.first().userId
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
            val userid = dataStorePreferenceStorage.userProfile.first().userId
            collectionReference.document(userid).collection(TRANSACTION_COLLECTION)
                .add(transactionDetailsModel).await()
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
            val userid = dataStorePreferenceStorage.userProfile.first().userId
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
            val userid = dataStorePreferenceStorage.userProfile.first().userId
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
            val userid = dataStorePreferenceStorage.userProfile.first().userId
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
            val userid = dataStorePreferenceStorage.userProfile.first().userId
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
            val userid = dataStorePreferenceStorage.userProfile.first().userId
            collectionReference.document(userid).collection(categoryDataModel.transactionType)
                .document(categoryDataModel.categoryName).set(categoryDataModel).await()
            successListener.invoke("Created")
        } catch (e: Exception) {
            failureListener.invoke(e)
        }
    }
}
