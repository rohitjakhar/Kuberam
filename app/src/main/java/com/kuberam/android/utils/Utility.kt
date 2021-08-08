package com.kuberam.android.utils

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import hu.ma.charts.legend.data.LegendEntry
import hu.ma.charts.legend.data.LegendPosition
import hu.ma.charts.pie.data.PieChartData
import hu.ma.charts.pie.data.PieChartEntry
import kotlin.math.roundToInt

@Composable
internal fun buildValuePercentString(item: LegendEntry) = buildAnnotatedString {
    item.value?.let { value ->
        withStyle(
            style = MaterialTheme.typography.body2.toSpanStyle()
                .copy(color = MaterialTheme.colors.primary)
        ) {
            append(value.toInt().toString())
        }
        append(" ")
    }

    withStyle(
        style = MaterialTheme.typography.caption.toSpanStyle()
            .copy(color = MaterialTheme.colors.secondary)
    ) {
        val percentString = item.percent.roundToInt().toString()
        append("($percentString %)")
    }
}

internal val PieSampleData =
    PieChartData(
        entries = listOf(220f, 54f, 600f, 60f, 140f, 200f).mapIndexed { index, value ->
            PieChartEntry(
                value,
                label = AnnotatedString("Test Chart $index")
            )
        },
        colors = listOf(
            Color.Black,
            Color.Blue,
            Color.Yellow,
            Color.Red,
            Color.LightGray,
            Color.Magenta,
            Color.Cyan,
            Color.Green,
            Color.Gray,
        ),
        legendPosition = LegendPosition.Bottom,
        legendShape = CircleShape
    )