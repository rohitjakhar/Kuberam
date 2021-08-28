package com.kuberam.android.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.Constant.INCOME_DATA
import com.kuberam.android.utils.cardBackground
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun TransactionComponenet(
    transactionDetailsModel: TransactionDetailsModel,
    viewModel: MainViewModel
) {
    val dateformatter = SimpleDateFormat("dd-MM-yy")
    val currentCurrency = produceState(initialValue = "$", key1 = viewModel.currency.value) {
        value = viewModel.currency.value
    }
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    ConstraintLayout(
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(cardBackground(isDarkTheme.value))
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        val (type, transactionBox, category, amount) = createRefs()
        if (transactionDetailsModel.transactionType == INCOME_DATA) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.constrainAs(type) {
                    top.linkTo(parent.top, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                }
            )
        } else {
            Icon(
                Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.constrainAs(type) {
                    top.linkTo(parent.top, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                }
            )
        }
        Column(
            Modifier.constrainAs(transactionBox) {
                top.linkTo(parent.top, 8.dp)
                bottom.linkTo(parent.bottom, 8.dp)
                start.linkTo(type.end, 8.dp)
            }
        ) {
            Text(transactionDetailsModel.transactionTitle)
            Text(transactionDetailsModel.transactionCategory)
        }
        Column(
            Modifier.constrainAs(amount) {
                top.linkTo(parent.top, 8.dp)
                end.linkTo(parent.end, 8.dp)
                bottom.linkTo(parent.bottom, 8.dp)
            }
        ) {
            Text(transactionDetailsModel.transactionAmount + currentCurrency.value)
            Text(dateformatter.format(transactionDetailsModel.transactionDate))
        }
    }
}
