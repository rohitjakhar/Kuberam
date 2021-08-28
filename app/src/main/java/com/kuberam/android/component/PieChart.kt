package com.kuberam.android.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kuberam.android.R
import com.kuberam.android.utils.CustomVerticalLegend
import hu.ma.charts.pie.PieChart
import hu.ma.charts.pie.data.PieChartData
import hu.ma.charts.pie.data.PieChartEntry

@Composable
fun SinglePieChart(pieChartData: List<PieChartEntry>, title: String) {
    val chartComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.chart_motion)
    )
    ChartContainer(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(horizontal = 16.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .animateContentSize(),
        title = title,
    ) {
        if (pieChartData.isEmpty()) {
            Box {
                LottieAnimation(
                    chartComposition,
                    restartOnPlay = false,
                    iterations = LottieConstants.IterateForever
                )
            }
        } else {
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
}
