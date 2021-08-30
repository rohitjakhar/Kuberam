package com.kuberam.android.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.kuberam.android.utils.cardBackground

@ExperimentalPagerApi
@Composable
fun OnBoardBottomSection(
    size: Int,
    index: Int,
    state: PagerState,
    isDarkThem: Boolean,
    onNextClicked: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {

        if (index + 1 == size) {
            ExtendedFloatingActionButton(
                onClick = onNextClicked,
                modifier = Modifier.align(Alignment.CenterEnd).padding(bottom = 8.dp, end = 8.dp),
                backgroundColor = cardBackground(isDarkThem),
                icon = {
                    Icon(Icons.Outlined.Done, contentDescription = "next image")
                },
                text = {
                    Text("Start")
                }
            )
        } else {
            HorizontalPagerIndicator(
                pagerState = state,
                modifier = Modifier.align(Alignment.Center),
                indicatorHeight = 10.dp,
                indicatorWidth = 28.dp
            )
        }
    }
}
