package com.kuberam.android.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kuberam.android.component.OnBoardComponent
import com.kuberam.android.navigation.Screen
import com.kuberam.android.utils.OnBoardItem
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun OnBoardScreen(navController: NavController) {
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        val items = OnBoardItem.get()
        val state = rememberPagerState(pageCount = items.size)
        HorizontalPager(
            state = state,
            modifier = Modifier.fillMaxSize()
                .weight(0.8f)
        ) { page ->
            OnBoardComponent(items[page], navController)
        }
        BottomSection(size = items.size, index = state.currentPage, state) {
            if (state.currentPage + 1 < items.size) {
                scope.launch { state.scrollToPage(state.currentPage + 1) }
            }
            if (state.currentPage + 1 == items.size) {
                scope.launch {
                    navController.navigate(Screen.Login.route)
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun BottomSection(
    size: Int,
    index: Int,
    state: PagerState,
    onNextClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        FloatingActionButton(
            onClick = onNextClicked,
            modifier = Modifier.align(Alignment.CenterEnd),
            backgroundColor = Color.Magenta,
            contentColor = Color.Cyan
        ) {
            if (index + 1 == size) {
                Icon(Icons.Outlined.Done, contentDescription = "nextimage")
            } else {
                Icon(Icons.Outlined.KeyboardArrowRight, contentDescription = "nextimage")
            }
        }
        HorizontalPagerIndicator(
            pagerState = state,
            modifier = Modifier.align(Alignment.CenterStart),
            indicatorHeight = 10.dp,
            indicatorWidth = 28.dp
        )
    }
}
