package com.kuberam.android.ui.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kuberam.android.component.AddTransactionBottomSheet
import com.kuberam.android.component.CategoryAddBottomSheet
import com.kuberam.android.component.LoadingComponent
import com.kuberam.android.component.MyBottomBar
import com.kuberam.android.component.TransactionComponenet
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.CustomVerticalLegend
import com.kuberam.android.utils.NetworkResponse
import hu.ma.charts.pie.PieChart
import hu.ma.charts.pie.data.PieChartData
import hu.ma.charts.pie.data.PieChartEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    val allTransaction by viewModel.allTransaction
    val categoryData by viewModel.incomeData
    val userProfile by viewModel.userProfile
    Scaffold(
        scaffoldState = scaffoldState,
        drawerShape = RectangleShape,
        bottomBar = {
            MyBottomBar(drawerState, navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch { addTransactionSheet.show() }
                },
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        content = { innerPaddding ->
            BottomDrawer(
                drawerState = drawerState,
                drawerContent = {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Card {
                            Text(
                                text = "Add Category",
                                modifier = Modifier.clickable {
                                    scope.launch {
                                        if (categoryAddSheet.isVisible) categoryAddSheet.hide() else categoryAddSheet.show()
                                    }
                                }
                            )
                        }
                    }
                },
                modifier = Modifier.padding(innerPaddding),
                gesturesEnabled = false
            ) {
                Column {
                    val chartList = arrayListOf<PieChartEntry>()
                    when (categoryData) {
                        is NetworkResponse.Success -> {
                            categoryData.data?.forEach {
                                chartList.add(
                                    PieChartEntry(
                                        value = it.amount.toFloat(),
                                        label = AnnotatedString(it.categoryName),
                                        color = Color(1538119760)
                                    )
                                )
                            }
                        }
                        is NetworkResponse.Loading -> {
                            LoadingComponent()
                        }
                        is NetworkResponse.Failure -> {
                        }
                    }
                    SinglePieChart(chartList)
                    Row {
                        Box {
                            Text("Income: ${userProfile.data?.name}")
                        }
                        Box {
                            Text("Expense: ${userProfile.data?.name}")
                        }
                    }
                    Spacer(Modifier.padding(top = 16.dp))
                    when (allTransaction) {
                        is NetworkResponse.Success -> {
                            LazyColumn {
                                items(allTransaction.data!!) { item: TransactionDetailsModel ->
                                    TransactionComponenet(item)
                                }
                            }
                        }
                        is NetworkResponse.Loading -> {
                            LoadingComponent()
                        }
                        is NetworkResponse.Failure -> {
                        }
                    }
                }
            }
        }
    )
    CategoryAddBottomSheet(categoryAddSheet, viewModel)
    AddTransactionBottomSheet(addTransactionSheet, viewModel)
}

@Composable
fun SinglePieChart(pieChartData: List<PieChartEntry>) {
    ChartContainer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .animateContentSize(),
        title = "Kubram Chart"
    ) {
        PieChart(
            data = PieChartData(
                entries = pieChartData
            ),
            legend = { entries ->
                CustomVerticalLegend(entries = entries)
            }
        )
    }
}

@Composable
fun ChartContainer(
    modifier: Modifier = Modifier,
    title: String,
    chartOffset: Dp = 12.dp,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        Text(title, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.requiredSize(chartOffset))
        content()
    }
}
