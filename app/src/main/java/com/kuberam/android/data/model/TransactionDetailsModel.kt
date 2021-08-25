package com.kuberam.android.data.model

import androidx.annotation.Keep
import com.google.firebase.firestore.ServerTimestamp
import java.util.Calendar
import java.util.Date

@Keep
data class TransactionDetailsModel(
    val transactionId: String = "",
    val transactionType: String = "",
    val transactionAmount: String = "",
    val transactionCategory: String = "",
    val transactionTitle: String = "",
    @ServerTimestamp
    val transactionDate: Date = Calendar.getInstance().time,
)
