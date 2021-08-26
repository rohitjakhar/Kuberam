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
                OnBoardItem(R.string.onbaord_title1, R.string.onboard_text1, R.drawable.ic_onboard1),
                OnBoardItem(R.string.onbaord_title2, R.string.onboard_text2, R.drawable.ic_onboard2),
                OnBoardItem(R.string.onbaord_title3, R.string.onboard_text3, R.drawable.ic_onboard3),
            )
        }
    }
}
