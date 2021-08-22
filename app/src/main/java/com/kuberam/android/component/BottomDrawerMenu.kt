package com.kuberam.android.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuberam.android.ui.view.SinglePieChart

@ExperimentalMaterialApi
@Composable
fun BottomDrawerMenu(
    drawerState: BottomSheetScaffoldState,
    innerPaddding: PaddingValues
) {
    BottomSheetScaffold(
        scaffoldState = drawerState,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(text = "Drawer Menu")
            }
        },
        sheetPeekHeight = 8.dp,
        modifier = Modifier.padding(innerPaddding),
    ) {
        //SinglePieChart()
        Spacer(Modifier.padding(top = 16.dp))
        LazyColumn {
        }
    }
}
