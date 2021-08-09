package com.kuberam.android.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuberam.android.data.DataStorePreferenceStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStorePreferenceStorage: DataStorePreferenceStorage
) : ViewModel() {
    private var _isLogin: MutableLiveData<Boolean> = MutableLiveData()
    val isLogin: LiveData<Boolean> get() = _isLogin

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isLogin.postValue(dataStorePreferenceStorage.isLogin.first())
        }
    }
}
