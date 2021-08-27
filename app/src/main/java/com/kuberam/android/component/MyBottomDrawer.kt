package com.kuberam.android.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.kuberam.android.R
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.NetworkResponse
import hu.ma.charts.pie.data.PieChartEntry
import kotlinx.coroutines.launch

@SuppressLint("Range")
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MyBottomDrawer(
    drawerState: BottomDrawerState,
    viewModel: MainViewModel,
    categoryAddSheet: ModalBottomSheetState,
    innerPaddding: PaddingValues
) {
    val lockCheckState = remember { mutableStateOf(true) }
    val pagerState = rememberPagerState(pageCount = 2)
    val allTransaction by viewModel.allTransaction
    val incomeCategoryData by viewModel.incomeData
    val expenseCategoryData by viewModel.expenseData
    val userProfile by viewModel.userProfileData
    val darkTheme = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel.darkTheme, viewModel.appLock) {
        viewModel.checkTheme()
        viewModel.checkAppLock()
        lockCheckState.value = viewModel.appLock.value
        darkTheme.value = viewModel.darkTheme.value
    }
    val scope = rememberCoroutineScope()
    BottomDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Row(Modifier.fillMaxWidth().padding(16.dp)) {
                    Icon(Icons.Default.Star, contentDescription = "theme")
                    Text(
                        text = "Add Category",
                        modifier = Modifier.clickable {
                            scope.launch {
                                drawerState.close()
                                categoryAddSheet.show()
                            }
                        }.fillMaxWidth()
                    )
                }
                Divider(color = Color.LightGray)
                Box(Modifier.fillMaxWidth().padding(16.dp)) {
                    Row {
                        Icon(Icons.Default.Star, contentDescription = "theme")
                        Text(text = "Dark Theme")
                    }
                    Switch(
                        checked = darkTheme.value,
                        onCheckedChange = {
                            darkTheme.value = it
                            viewModel.changeTheme(it)
                        },
                        modifier = Modifier.align(
                            Alignment.CenterEnd
                        )
                    )
                }
                Divider(color = Color.LightGray)
                Box(Modifier.fillMaxWidth().padding(16.dp)) {
                    Row {
                        Icon(Icons.Default.Lock, contentDescription = "theme")
                        Text(text = "Enable Lock")
                    }
                    Switch(
                        checked = lockCheckState.value,
                        onCheckedChange = { lockCheckState.value = it },
                        modifier = Modifier.align(
                            Alignment.CenterEnd
                        )
                    )
                }
                Divider(color = Color.LightGray)
            }
        },
        modifier = Modifier.padding(innerPaddding),
        gesturesEnabled = false
    ) {
        Column {
            CustomTopSection(viewModel)
            Spacer(Modifier.padding(top = 16.dp))
            val incomeChartList = arrayListOf<PieChartEntry>()
            val expenseChartList = arrayListOf<PieChartEntry>()
            when (incomeCategoryData) {
                is NetworkResponse.Success -> {
                    incomeCategoryData.data?.forEach {
                        incomeChartList.add(
                            PieChartEntry(
                                value = it.amount.toFloat(),
                                label = AnnotatedString(it.categoryName),
                                color = Color(android.graphics.Color.parseColor(it.colorCode))
                            )
                        )
                    }
                }
                is NetworkResponse.Loading -> {
                    LoadingComponent()
                }
                is NetworkResponse.Failure -> {
                    Box(Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(R.drawable.ic_add_data),
                            contentDescription = null,
                            modifier = Modifier.wrapContentSize()
                        )
                        Text("Empty Data")
                    }
                }
            }
            when (expenseCategoryData) {
                is NetworkResponse.Success -> {
                    expenseCategoryData.data?.forEach {
                        expenseChartList.add(
                            PieChartEntry(
                                value = it.amount.toFloat(),
                                label = AnnotatedString(it.categoryName),
                                color = Color(android.graphics.Color.parseColor(it.colorCode))
                            )
                        )
                    }
                }
                is NetworkResponse.Loading -> {
                    LoadingComponent()
                }
                is NetworkResponse.Failure -> {
                    Box(Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(R.drawable.ic_add_data),
                            contentDescription = null,
                            modifier = Modifier.wrapContentSize()
                        )
                        Text("Empty Data")
                    }
                }
            }

            HorizontalPager(
                pagerState,
            ) { page ->
                if (page == 0) {
                    SinglePieChart(incomeChartList, title = "Income")
                } else {
                    SinglePieChart(expenseChartList, "Expense")
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState, indicatorShape = CircleShape,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ).padding(16.dp),
            )
            Spacer(Modifier.padding(top = 16.dp))
            TransactionList(viewModel)
        }
    }
}
