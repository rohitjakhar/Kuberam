package com.kuberam.android.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kuberam.android.R

// Set of Material typography styles to start with
val Amiko = FontFamily(
    Font(R.font.amiko),
    Font(R.font.amiko_semibold, weight = FontWeight.SemiBold),
    Font(R.font.amiko_bold, weight = FontWeight.Bold),
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Amiko,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = Amiko,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h2 = TextStyle(
        fontFamily = Amiko,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    h3 = TextStyle(
        fontFamily = Amiko,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontFamily = Amiko,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )

/* Other default text styles to override
button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
),
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)
