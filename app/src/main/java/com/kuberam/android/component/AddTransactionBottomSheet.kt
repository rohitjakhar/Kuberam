package com.kuberam.android.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuberam.android.ui.view.AddTransaction
import com.kuberam.android.ui.viewmodel.MainViewModel

@ExperimentalMaterialApi
@Composable
fun AddTransactionBottomSheet(
    addTransactionSheet: ModalBottomSheetState,
    viewModel: MainViewModel
) {
    ModalBottomSheetLayout(
        sheetState = addTransactionSheet,
        sheetShape = MaterialTheme.shapes.large,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                AddTransaction(viewModel = viewModel)
            }
        },
        scrimColor = DrawerDefaults.scrimColor,
        sheetElevation = 16.dp,
    ) {}
}
