package com.kuberam.android.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.utils.Constant.INCOME_DATA
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun TransactionComponenet(transactionDetailsModel: TransactionDetailsModel) {
    val dateformatter = SimpleDateFormat("dd-MM-yyyy")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xff51E1ED))
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            Text(
                text = dateformatter.format(transactionDetailsModel.transactionDate)
            )
        }
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = transactionDetailsModel.transactionTitle,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = transactionDetailsModel.transactionCategory,
                style = MaterialTheme.typography.h5
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .padding(10.dp)
        ) {
            Text(
                text = transactionDetailsModel.transactionAmount,
                color = if (transactionDetailsModel.transactionType == INCOME_DATA) Color.Red else Color.Blue
            )
        }
    }
}
