package com.kuberam.android.utils

import androidx.compose.runtime.Composable
import com.kuberam.android.ui.theme.backGroundDay
import com.kuberam.android.ui.theme.backgroundNight

@Composable
fun ButtonBackground(isDarkTheme: Boolean) = if (isDarkTheme) {
    backgroundNight
} else {
    backGroundDay
}

@Composable
fun TextHeadingColor(isDarkTheme: Boolean) = if (isDarkTheme){

} else{

}

@Composable
fun textNormalColor(isDarkTheme: Boolean) = if (isDarkTheme){

} else {

}

@Composable
fun CardBackground(isDarkTheme: Boolean) = if (isDarkTheme){

} else {

}

@Composable
fun StrokeColor(isDarkTheme: Boolean) = if (isDarkTheme){

} else {

}

fun ChipColor(isDarkTheme: Boolean) = if (isDarkTheme){

} else {

}