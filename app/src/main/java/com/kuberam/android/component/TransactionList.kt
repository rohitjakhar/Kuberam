package com.kuberam.android.component

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
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kuberam.android.R
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.NetworkResponse

@ExperimentalMaterialApi
@Composable
fun TransactionList(viewModel: MainViewModel) {
    val transactionComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.transaction_motion)
    )
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
    if (transactionListState.value.isEmpty()) {
        Box {
            LottieAnimation(
                composition = transactionComposition, iterations = LottieConstants.IterateForever
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
                        if (it == DismissValue.DismissedToStart) {
                            viewModel.deleteTransaction(item)
                            viewModel.getAllTransaction()
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = state,
                    background = {
                        val color = when (state.dismissDirection) {
                            DismissDirection.StartToEnd -> Color.Transparent
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
                                modifier = Modifier.align(Alignment.CenterEnd).padding(end = 8.dp)
                            )
                        }
                    },
                    dismissContent = {
                        TransactionComponenet(item, viewModel)
                    },
                    directions = setOf(
                        DismissDirection.EndToStart
                    )
                )
            }
        }
    }
}
