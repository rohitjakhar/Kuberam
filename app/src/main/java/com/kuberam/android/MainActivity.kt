package com.kuberam.android

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.messaging.FirebaseMessaging
import com.kuberam.android.navigation.KuberamNavigation
import com.kuberam.android.ui.theme.KuberamTheme
import com.kuberam.android.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {
    @ExperimentalComposeUiApi
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            FirebaseMessaging.getInstance().subscribeToTopic("Transaction")
            KuberamMain(viewModel)
        }
    }

    @ExperimentalComposeUiApi
    @RequiresApi(Build.VERSION_CODES.N)
    @Composable
    private fun KuberamMain(viewModel: MainViewModel) {
        LaunchedEffect(true) {
            viewModel.checkTheme()
        }
        val darkMode = viewModel.darkTheme.value
        KuberamTheme(darkMode) {
            // A surface container using the 'background' color from the theme
            Surface {
                KuberamNavigation(viewModel)
            }
        }
    }
}
