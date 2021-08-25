package com.kuberam.android.utils

import com.kuberam.android.R

class OnBoardItem(
    val title: Int,
    val text: Int,
    val image: Int
) {
    companion object {
        fun get(): List<OnBoardItem> {
            return listOf(
                OnBoardItem(R.string.onbaord_title1, R.string.onboard_text1, R.drawable.ic_add_data),
                OnBoardItem(R.string.onbaord_title1, R.string.onboard_text1, R.drawable.ic_add_data),
                OnBoardItem(R.string.onbaord_title1, R.string.onboard_text1, R.drawable.ic_add_data),
                OnBoardItem(R.string.onbaord_title1, R.string.onboard_text1, R.drawable.ic_add_data),
            )
        }
    }
}
