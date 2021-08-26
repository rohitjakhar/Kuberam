package com.kuberam.android.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Calendar
import java.util.Date

data class FeedbackModel(
    val userId: String,
    val userName: String,
    @ServerTimestamp
    val transactionDate: Date = Calendar.getInstance().time,
    val feedback: String
)
