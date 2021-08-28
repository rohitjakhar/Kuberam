package com.kuberam.android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.cardBackground
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun MyBottomBar(
    drawerState: BottomDrawerState,
    navController: NavController,
    viewModel: MainViewModel
) {
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    val scope = rememberCoroutineScope()
    BottomAppBar(
        cutoutShape = CircleShape,
        elevation = 16.dp,
        modifier = Modifier.background(Color.Transparent),
        contentPadding = PaddingValues(6.dp),
        backgroundColor = cardBackground(isDarkTheme.value)
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
