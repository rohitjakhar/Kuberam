package com.kuberam.android.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kuberam.android.utils.OnBoardItem

@Composable
fun OnBoardComponent(item: OnBoardItem, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(item.image), contentDescription = "image")
        Text(
            text = stringResource(item.title),
            fontSize = 24.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(item.text),
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
    }
} 
