package com.kuberam.android.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kuberam.android.utils.CustomVerticalLegend
import hu.ma.charts.pie.PieChart
import hu.ma.charts.pie.data.PieChartData
import hu.ma.charts.pie.data.PieChartEntry

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
        title = "Kuberam Chart"
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