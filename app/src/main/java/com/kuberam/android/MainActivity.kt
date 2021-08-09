package com.kuberam.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.kuberam.android.data.DataStorePreferenceStorage
import com.kuberam.android.navigation.KuberamNavigation
import com.kuberam.android.ui.theme.KuberamTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    @Inject lateinit var dataStorePreferenceStorage: DataStorePreferenceStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isLogin by dataStorePreferenceStorage.isLogin.collectAsState(initial = true)
            KuberamTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    KuberamNavigation(isLogin)
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
