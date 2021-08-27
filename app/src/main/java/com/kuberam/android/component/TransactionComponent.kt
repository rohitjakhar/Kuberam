package com.kuberam.android.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kuberam.android.data.model.TransactionDetailsModel
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun TransactionComponenet(transactionDetailsModel: TransactionDetailsModel) {
    val dateformatter = SimpleDateFormat("dd-MM-yy")
    ConstraintLayout(
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        val (date, transactionBox, category, amount) = createRefs()

        Text(
            text = dateformatter.format(transactionDetailsModel.transactionDate),
            modifier = Modifier.constrainAs(date) {
                top.linkTo(parent.top, 8.dp)
                bottom.linkTo(parent.bottom,8.dp)
            }
        )
        Column(
            Modifier.constrainAs(transactionBox) {
                top.linkTo(parent.top, 8.dp)
                bottom.linkTo(parent.bottom, 8.dp)
                start.linkTo(date.end, 8.dp)
            }
        ) {
            Text(transactionDetailsModel.transactionTitle)
            Text(transactionDetailsModel.transactionCategory)
        }

        Text(
            text = transactionDetailsModel.transactionAmount,
            Modifier.constrainAs(amount) {
                top.linkTo(parent.top, 8.dp)
                end.linkTo(parent.end, 8.dp)
                bottom.linkTo(parent.bottom, 8.dp)
            }
        )
    }
}
