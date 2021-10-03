package com.kuberam.android.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.kuberam.android.data.local.DataStorePreferenceStorage
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.utils.Constant
import com.kuberam.android.utils.NetworkResponse
import com.kuberam.android.utils.safeFirebaseCall
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class FetchTransaction @Inject constructor(
    private val dataStorePreferenceStorage: DataStorePreferenceStorage,
    @Named("userCollectionReference") private val userCollectionReference: CollectionReference
) {

    val userId = dataStorePreferenceStorage.userProfileData

    suspend fun getAllTransaction(): NetworkResponse<List<TransactionDetailsModel>> {
        val userid = userId.first().userId
        return safeFirebaseCall(nullDataMessage = "Empty list", handleNullChecking = true) {
            userCollectionReference.document(userid).collection(Constant.TRANSACTION_COLLECTION)
                .orderBy("transactionDate", Query.Direction.DESCENDING).get().await()
                .toObjects(TransactionDetailsModel::class.java)
        }
    }

    suspend fun getIncomeData(): NetworkResponse<List<CategoryDataModel>> {
        val userid = userId.first().userId
        return safeFirebaseCall(handleNullChecking = true, nullDataMessage = "Empty list") {
            userCollectionReference.document(userid).collection(Constant.INCOME_DATA).get()
                .await().toObjects(CategoryDataModel::class.java)
        }
    }

    suspend fun getExpenseData(): NetworkResponse<List<CategoryDataModel>> {
        val userid = userId.first().userId
        return safeFirebaseCall(handleNullChecking = true, nullDataMessage = "Empty list") {
            userCollectionReference.document(userid).collection(Constant.EXPENSE_DATA).get()
                .await().toObjects(CategoryDataModel::class.java)
        }
    }
}
