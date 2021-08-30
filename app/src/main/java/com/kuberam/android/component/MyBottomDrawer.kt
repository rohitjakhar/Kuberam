package com.kuberam.android.component

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.kuberam.android.R
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.Constant.EXPENSE_DATA
import com.kuberam.android.utils.Constant.INCOME_DATA
import com.kuberam.android.utils.currentList
import com.kuberam.android.utils.textHeadingColor
import com.kuberam.android.utils.textNormalColor
import hu.ma.charts.pie.data.PieChartEntry
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("Range")
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MyBottomDrawer(
    drawerState: BottomDrawerState,
    viewModel: MainViewModel,
    categoryAddSheet: ModalBottomSheetState,
    innerPadding: PaddingValues,
    navController: NavController
) {
    val lockCheckState = remember { mutableStateOf(true) }
    val pagerState = rememberPagerState(pageCount = 2)
    val darkTheme = remember { mutableStateOf(false) }
    val currencyState = remember { mutableStateOf("") }
    val showCurrencyDialog = remember { mutableStateOf(false) }

    val incomeCategoryState =
        produceState(initialValue = emptyList<CategoryDataModel>(), viewModel.incomeData.value) {
            value = viewModel.incomeData.value
        }
    val expenseCategoryState =
        produceState(initialValue = emptyList<CategoryDataModel>(), viewModel.expenseData.value) {
            value = viewModel.expenseData.value
        }
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    LaunchedEffect(key1 = viewModel.darkTheme, viewModel.appLock, viewModel.currency.value) {
        viewModel.checkTheme()
        viewModel.checkAppLock()
        viewModel.getCurrentCurrency()
        currencyState.value = viewModel.currency.value
        lockCheckState.value = viewModel.appLock.value
        darkTheme.value = viewModel.darkTheme.value
    }
    val scope = rememberCoroutineScope()
    BottomDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                Modifier
                    .fillMaxWidth().wrapContentHeight()
            ) {
                viewModel.getCurrentCurrency()
                Row(Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.add_category),
                        modifier = Modifier.clickable {
                            scope.launch {
                                drawerState.close()
                                categoryAddSheet.show()
                            }
                        }.fillMaxWidth(),
                        color = textNormalColor(isDarkTheme.value)
                    )
                }
                Divider(color = Color.LightGray)
                Box(Modifier.fillMaxWidth().padding(16.dp)) {
                    Row {
                        Text(
                            text = stringResource(R.string.dark_theme),
                            color = textNormalColor(isDarkTheme.value)
                        )
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
                Box(
                    Modifier.fillMaxWidth().padding(16.dp).clickable {
                        showCurrencyDialog.value = true
                    }
                ) {
                    Row {
                        Text(
                            text = stringResource(R.string.change_currency),
                            color = textNormalColor(isDarkTheme.value)
                        )
                    }
                    Text(
                        text = currencyState.value,
                        modifier = Modifier.align(
                            Alignment.CenterEnd
                        ).padding(end = 4.dp),
                        color = textNormalColor(isDarkTheme.value)
                    )
                    if (showCurrencyDialog.value) {
                        AlertDialog(
                            onDismissRequest = {
                                viewModel.getCurrentCurrency()
                                showCurrencyDialog.value = false
                            },
                            text = {
                                Column {
                                    currentList.forEach {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.h2,
                                            modifier = Modifier.clickable {
                                                viewModel.changeCurrency(it)
                                                viewModel.getCurrentCurrency()
                                                showCurrencyDialog.value = false
                                            }.padding(8.dp),
                                            color = textNormalColor(isDarkTheme.value)
                                        )
                                    }
                                }
                            },
                            title = {
                                Text(
                                    text = stringResource(R.string.select_currency),
                                    style = MaterialTheme.typography.h1,
                                    color = textHeadingColor(isDarkTheme.value)
                                )
                            },
                            buttons = {},
                            shape = RoundedCornerShape(size = 16.dp),
                        )
                    }
                }
                Divider(color = Color.LightGray)
                Box(Modifier.fillMaxWidth().padding(16.dp)) {
                    Row {
                        Text(
                            text = "Enable Lock",
                            color = textNormalColor(isDarkTheme.value)
                        )
                    }
                    Switch(
                        checked = lockCheckState.value,
                        onCheckedChange = {
                            lockCheckState.value = it
                            viewModel.changeAppLock(it)
                        },
                        modifier = Modifier.align(
                            Alignment.CenterEnd
                        )
                    )
                }
                Divider(color = Color.LightGray)
            }
        },
        modifier = Modifier.padding(innerPadding),
        gesturesEnabled = false
    ) {
        Column {
            CustomTopSection(viewModel)
            Spacer(Modifier.padding(top = 8.dp))
            val incomeChartList = arrayListOf<PieChartEntry>()
            val expenseChartList = arrayListOf<PieChartEntry>()
            incomeCategoryState.value.forEach {
                incomeChartList.add(
                    PieChartEntry(
                        value = it.amount.toFloat(),
                        label = AnnotatedString(it.categoryName),
                        color = Color(android.graphics.Color.parseColor(it.colorCode))
                    )
                )
            }
            expenseCategoryState.value.forEach {
                expenseChartList.add(
                    PieChartEntry(
                        value = it.amount.toFloat(),
                        label = AnnotatedString(it.categoryName),
                        color = Color(android.graphics.Color.parseColor(it.colorCode))
                    )
                )
            }
            HorizontalPager(
                pagerState,
            ) { page ->
                if (page == 0) {
                    SinglePieChart(incomeChartList, title = INCOME_DATA)
                } else {
                    SinglePieChart(expenseChartList, EXPENSE_DATA)
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState, indicatorShape = CircleShape,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ).padding(16.dp),
            )
            Spacer(Modifier.padding(top = 8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(start = 6.dp, end = 6.dp)
            ) {
                Text(
                    text = stringResource(R.string.recent_transaction),
                    style = MaterialTheme.typography.body1,
                    color = textNormalColor(isDarkTheme.value)
                )
                Text(
                    text = stringResource(R.string.see_all),
                    Modifier.clickable {
                        navController.navigate(Screen.TransactionsScreen.route)
                    },
                    style = MaterialTheme.typography.body1,
                    color = textNormalColor(isDarkTheme.value)
                )
            }
            TransactionList(viewModel)
        }
    }
}
