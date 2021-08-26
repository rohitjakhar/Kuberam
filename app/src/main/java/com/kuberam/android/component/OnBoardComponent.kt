package com.kuberam.android.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = stringResource(item.title),
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.padding(6.dp))
        Text(
            text = stringResource(item.text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
        )
    }
} 
