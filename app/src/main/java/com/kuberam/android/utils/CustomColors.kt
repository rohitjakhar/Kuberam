package com.kuberam.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.kuberam.android.ui.theme.backGroundDay
import com.kuberam.android.ui.theme.backgroundNight
import com.kuberam.android.ui.theme.cardBackgroundDay
import com.kuberam.android.ui.theme.cardBackgroundNight
import com.kuberam.android.ui.theme.textHeadingDay
import com.kuberam.android.ui.theme.textHeadingNight
import com.kuberam.android.ui.theme.textNormalDay
import com.kuberam.android.ui.theme.textNormalNight

@Composable
fun ButtonBackground(isDarkTheme: Boolean) = if (isDarkTheme) {
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

@Composable
fun StrokeColor(isDarkTheme: Boolean) = if (isDarkTheme) {
} else {
}

fun chipColor(isDarkTheme: Boolean) = if (isDarkTheme) {
    Color(0xff274661)
} else {
    Color.DarkGray
}

fun selectedChipColor(isDarkTheme: Boolean) = if (isDarkTheme) {
    cardBackgroundNight
} else {
    cardBackgroundDay
}

fun focusOutlineBorderColor(isDarkTheme: Boolean) = if (isDarkTheme) {
    Color(0xfff4c057)
} else {
    Color(0xfffdc9e4)
}

fun unfocusOutlineBorderColor(isDarkTheme: Boolean) = if (isDarkTheme) {
    Color(0xff2d4057)
} else {
    Color(0xffabeacf)
}

fun textBoxBrush(isDarkTheme: Boolean) = if (isDarkTheme) {
    Brush.horizontalGradient(colors = listOf(Color(0xff5c38a9), Color(0xff174450)))
} else {
    Brush.horizontalGradient(colors = listOf(Color(0xff7ae87a), Color(0xff1ff2c8)))
}
