package com.kuberam.android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kuberam.android.navigation.Screen
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun MyBottomBar(drawerState: BottomDrawerState, navController: NavController) {
    val scope = rememberCoroutineScope()
    BottomAppBar(
        cutoutShape = CircleShape,
        elevation = 16.dp,
        modifier = Modifier.background(Color.Transparent),
        contentPadding = PaddingValues(6.dp),
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            IconButton(
                onClick = {
                    scope.launch {
                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                    }
                }
            ) {
                Icon(Icons.Filled.Menu, contentDescription = "menu")
            }
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(
            onClick = {
                navController.navigate(Screen.Profile.route)
            }
        ) {
            Icon(Icons.Filled.Person, contentDescription = "profile")
        }
    }
}
