package com.kuberam.android.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow

interface PreferenceDataStore {
    val isLogin: Flow<Boolean>
    val isFirstTime: Flow<Boolean>

    suspend fun isLogin(isLogin: Boolean)
    suspend fun firstTime(isFirstTime: Boolean)
    suspend fun clearData()

    object PreferenceKey {
        val PREF_IS_LOGIN = booleanPreferencesKey(PREFERENCE_IS_LOGIN)
        val PREF_IS_FIRST_TIME = booleanPreferencesKey(PREFERENCE_IS_FIRST_TIME)
    }

    companion object {
        const val PREFERENCE_IS_LOGIN = "isLogin"
        const val PREFERENCE_IS_FIRST_TIME = "isFirstTime"
    }
}
