package com.kuberam.android.utils

import androidx.compose.runtime.Composable
import com.kuberam.android.ui.theme.backGroundDay
import com.kuberam.android.ui.theme.backgroundNight
import com.kuberam.android.ui.theme.cardBackgroundDay
import com.kuberam.android.ui.theme.cardBackgroundNight
import com.kuberam.android.ui.theme.chipDay
import com.kuberam.android.ui.theme.chipNight
import com.kuberam.android.ui.theme.focusTextBoxDay
import com.kuberam.android.ui.theme.focusTextBoxNight
import com.kuberam.android.ui.theme.textBoxBrushDay
import com.kuberam.android.ui.theme.textBoxBrushNight
import com.kuberam.android.ui.theme.textHeadingDay
import com.kuberam.android.ui.theme.textHeadingNight
import com.kuberam.android.ui.theme.textNormalDay
import com.kuberam.android.ui.theme.textNormalNight
import com.kuberam.android.ui.theme.unfocusedTextBoxDay
import com.kuberam.android.ui.theme.unfocusedTextBoxNight

@Composable
fun buttonBackground(isDarkTheme: Boolean) = if (isDarkTheme) {
    backgroundNight
} else {
    backGroundDay
}

@Composable
fun textHeadingColor(isDarkTheme: Boolean) = if (isDarkTheme) {
    textHeadingNight
} else {
    textHeadingDay
}

@Composable
fun textNormalColor(isDarkTheme: Boolean) = if (isDarkTheme) {
    textNormalNight
} else {
    textNormalDay
}

@Composable
fun cardBackground(isDarkTheme: Boolean) = if (isDarkTheme) {
    cardBackgroundNight
} else {
    cardBackgroundDay
}

fun chipColor(isDarkTheme: Boolean) = if (isDarkTheme) {
    chipNight
} else {
    chipDay
}

fun selectedChipColor(isDarkTheme: Boolean) = if (isDarkTheme) {
    cardBackgroundNight
} else {
    cardBackgroundDay
}

fun focusOutlineBorderColor(isDarkTheme: Boolean) = if (isDarkTheme) {
    focusTextBoxNight
} else {
    focusTextBoxDay
}

fun unfocusedOutlineBorderColor(isDarkTheme: Boolean) = if (isDarkTheme) {
    unfocusedTextBoxNight
} else {
    unfocusedTextBoxDay
}

fun textBoxBrush(isDarkTheme: Boolean) = if (isDarkTheme) {
    textBoxBrushNight
} else {
    textBoxBrushDay
}
