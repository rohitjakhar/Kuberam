package com.kuberam.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuberam.android.data.DataStorePreferenceStorage
import com.kuberam.android.data.model.ProfileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
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
        viewModelScope.launch(IO) {
            _isLogin.postValue(dataStorePreferenceStorage.isLogin.first())
        }
    }

    fun saveProfile(userProfile: ProfileModel) {
        viewModelScope.launch(IO) {
            dataStorePreferenceStorage.saveProfile(userProfile)
        }
    }

    fun changeLogin(isLogin: Boolean) {
        viewModelScope.launch(IO) {
            dataStorePreferenceStorage.isLogin(isLogin)
        }
    }
}
