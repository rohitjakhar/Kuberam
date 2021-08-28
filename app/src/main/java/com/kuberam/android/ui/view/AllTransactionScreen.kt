package com.kuberam.android.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kuberam.android.R
import com.kuberam.android.component.TransactionComponenet
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.NetworkResponse
import com.kuberam.android.utils.textNormalColor

@ExperimentalMaterialApi
@Composable
fun AllTransactionScreen(viewModel: MainViewModel, navController: NavController) {
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    var expended by remember { mutableStateOf(false) }
    val transactionListState = produceState(
        initialValue = emptyList<TransactionDetailsModel>(),
        key1 = viewModel.allTransaction.value
    ) {
        value = when (viewModel.allTransaction.value) {
            is NetworkResponse.Success<*> -> {
                viewModel.allTransaction.value.data!!
            }
            else -> {
                emptyList()
            }
        }
    }
    val transactionComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.transaction_motion)
    )

    Scaffold(
        topBar = {
            Text(
                text = "All Transaction",
                style = MaterialTheme.typography.h1,
                color = textNormalColor(isDarkTheme.value),
                modifier = Modifier.padding(8.dp)
            )
        }
    ) {
        if (transactionListState.value.isEmpty()) {
            Box {
                LottieAnimation(
                    composition = transactionComposition,
                    iterations = LottieConstants.IterateForever
                )
            }
        } else {
            LazyColumn {
                itemsIndexed(
                    items = transactionListState.value,
                    key = { index, item ->
                        item.hashCode()
                    }
                ) { index, item ->
                    val state = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd) {
                                viewModel.deleteTransaction(item)
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color = when (state.dismissDirection) {
                                DismissDirection.StartToEnd -> MaterialTheme.colors.error
                                DismissDirection.EndToStart -> MaterialTheme.colors.error
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .padding(18.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(color)
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                        .padding(end = 8.dp)
                                )
                            }
                        },
                        dismissContent = {
                            TransactionComponenet(item, viewModel)
                        },
                        directions = setOf(
                            DismissDirection.EndToStart,
                            DismissDirection.StartToEnd
                        )
                    )
                }
            }
        }
    }
}
