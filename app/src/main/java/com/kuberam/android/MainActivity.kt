package com.kuberam.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.kuberam.android.data.DataStorePreferenceStorage
import com.kuberam.android.navigation.KuberamNavigation
import com.kuberam.android.ui.theme.KuberamTheme
import com.kuberam.android.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStorePreferenceStorage: DataStorePreferenceStorage
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isLogin by dataStorePreferenceStorage.isLogin.collectAsState(false)
            KuberamTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Log.d("test65", "login: $isLogin")
                    KuberamNavigation(isLogin, viewModel = viewModel)
                }
            }
        }
        checkFirstTime()
    }

    private fun checkFirstTime() {
        lifecycleScope.launchWhenStarted {
            /*dataStorePreferenceStorage.isFirstTime.collect {

            }*/
        }
    }
}
