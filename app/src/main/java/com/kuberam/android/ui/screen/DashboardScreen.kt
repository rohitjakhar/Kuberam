package com.kuberam.android.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kuberam.android.navigation.Screen
import com.kuberam.android.utils.PieSampleData
import com.kuberam.android.utils.buildValuePercentString
import hu.ma.charts.legend.data.LegendEntry
import hu.ma.charts.pie.PieChart
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun DashboardScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)
    val (gesturesEnabled, toggleGesturesEnabled) = remember { mutableStateOf(true) }
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Test ")
            Text("Test ")
            Text("Test ")
            Text("Test ")
            Text("Test ")
        },

        drawerShape = RectangleShape,
        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape
            ) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                    IconButton(
                        onClick = {
                            scope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() }
                        }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "menu")
                    }
                }
                Spacer(Modifier.weight(1f, true))
                IconButton(
                    onClick = {
                        navController.navigate(Screen.Login.route)
                    }
                ) {
                    Icon(Icons.Filled.Person, contentDescription = "profile")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                },
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        content = {

            BottomDrawer(
                gesturesEnabled = gesturesEnabled,
                drawerElevation = 20.dp,
                drawerState = drawerState,
                drawerContent = {
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        onClick = { scope.launch { drawerState.close() } },
                        content = { Text("Close Drawer") }
                    )
                    Text("Test Rohit")
                    Text("Test Rohit")
                    Text("Test Rohit")
                    Text("Test Rohit")
                    Text("Test Rohit")
                }
            ) {
                SinglePieChart()
            }
        }

    )
}

@Preview(showBackground = true)
@Composable
fun SinglePieChart() {
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
            data = PieSampleData,
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

@Composable
internal fun RowScope.CustomVerticalLegend(entries: List<LegendEntry>) {
    Column(
        modifier = Modifier.weight(1f),
    ) {
        entries.forEachIndexed { idx, item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 14.dp)
            ) {
                Box(
                    Modifier
                        .requiredSize(item.shape.size)
                        .background(item.shape.color, item.shape.shape)
                )

                Spacer(modifier = Modifier.requiredSize(8.dp))

                Text(
                    text = item.text,
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = buildValuePercentString(item),
                    style = MaterialTheme.typography.caption,
                )
            }

            if (idx != entries.lastIndex)
                Divider()
        }
    }
}
