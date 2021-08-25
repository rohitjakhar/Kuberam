package com.kuberam.android.data.model

data class ProfileDataModel (
    val name: String = "",
    val email: String = "",
    val profileUrl: String = "",
    val userId: String = "",
    val totalIncome: Long = 0L,
    val totalExpense: Long = 0L
)
