package com.kuberam.android.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter

@Composable
fun ProfileScreen() {
    Scaffold {
        Box(contentAlignment = Alignment.Center){
            Image(
                painter = rememberImagePainter("https://lh3.googleusercontent.com/a-/AOh14GhMaoY1tL0jeC33Rx9jozbZseD-yrAYMUuInPjOiw=s96-c"),
                contentDescription = null,
            )
        }
    }
}
