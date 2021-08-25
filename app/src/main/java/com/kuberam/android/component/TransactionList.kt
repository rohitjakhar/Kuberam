package com.kuberam.android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.NetworkResponse

@ExperimentalMaterialApi
@Composable
fun TransactionList(viewModel: MainViewModel) {
    val incomeListState = produceState(
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
    LazyColumn {
        itemsIndexed(
            items = incomeListState.value,
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
                        DismissDirection.StartToEnd -> Color.Red
                        DismissDirection.EndToStart -> Color.Red
                        null -> Color.Transparent
                    }
                    Box(
                        modifier = Modifier.fillMaxSize().padding(8.dp)
                            .background(color)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                },
                dismissContent = {
                    TransactionComponenet(item)
                },
                directions = setOf(
                    DismissDirection.EndToStart,
                    DismissDirection.StartToEnd
                )
            )
        }
    }
}
