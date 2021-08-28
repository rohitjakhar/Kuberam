package com.kuberam.android.ui.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kuberam.android.component.AddCategoryBottomSheet
import com.kuberam.android.component.AddTransactionBottomSheet
import com.kuberam.android.component.MyBottomBar
import com.kuberam.android.component.MyBottomDrawer
import com.kuberam.android.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalPagerApi
@SuppressLint("Range")
@ExperimentalMaterialApi
@Composable
fun DashboardScreen(navController: NavController, viewModel: MainViewModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    viewModel.getAllTransaction()
    viewModel.getIncomeData()
    viewModel.getExpenseData()
    viewModel.getUserDetails()
    val addTransactionSheet =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val drawerState =
        rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)
    val categoryAddSheet =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    Scaffold(
        scaffoldState = scaffoldState,
        drawerShape = RectangleShape,
        bottomBar = {
            MyBottomBar(drawerState, navController, viewModel)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch { addTransactionSheet.show() }
                },
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp, pressedElevation = 27.dp),
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        content = { innerPaddding ->
            MyBottomDrawer(
                drawerState = drawerState,
                viewModel = viewModel,
                categoryAddSheet = categoryAddSheet,
                innerPaddding = innerPaddding,
                navController = navController
            )
        }
    )
    AddCategoryBottomSheet(categoryAddSheet, viewModel, scaffoldState)
    AddTransactionBottomSheet(addTransactionSheet, viewModel, categoryAddSheet, scaffoldState)
}
