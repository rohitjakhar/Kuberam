package com.kuberam.android.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.kuberam.android.component.OnBoardBottomSection
import com.kuberam.android.component.OnBoardComponent
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.OnBoardItem
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@Composable
fun OnBoardScreen(navController: NavController, viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            viewModel.checkTheme()
            value = viewModel.darkTheme.value
        }
    Column(Modifier.fillMaxSize()) {
        val items = OnBoardItem.get()
        val state = rememberPagerState(pageCount = items.size)
        HorizontalPager(
            state = state,
            modifier = Modifier.fillMaxSize()
                .weight(0.8f)
        ) { page ->
            Card(
                Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.55f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .fillMaxWidth()

                // We animate the alpha, between 50% and 100%
                /*alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )*/

            ) {
                // Card content
                OnBoardComponent(items[page], navController)
            }
        }
        OnBoardBottomSection(
            size = items.size,
            index = state.currentPage,
            state = state,
            isDarkThem = isDarkTheme.value
        ) {
            if (state.currentPage + 1 < items.size) {
                scope.launch { state.scrollToPage(state.currentPage + 1) }
            }
            if (state.currentPage + 1 == items.size) {
                scope.launch {
                    viewModel.changeFirstTime()
                    navController.navigate(Screen.Login.route)
                }
            }
        }
    }
}
