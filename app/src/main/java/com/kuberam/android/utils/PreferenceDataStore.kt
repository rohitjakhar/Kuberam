package com.kuberam.android.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kuberam.android.data.model.ProfileDataModel
import kotlinx.coroutines.flow.Flow

interface PreferenceDataStore {
    val isLogin: Flow<Boolean>
    val isFirstTime: Flow<Boolean>
    val userProfileData: Flow<ProfileDataModel>
    val isLockEnable: Flow<Boolean>
    val isDarkTheme: Flow<Boolean>
    val currentCurrency: Flow<String>

    suspend fun isLogin(isLogin: Boolean)
    suspend fun isLockEnable(isLockEnable: Boolean)
    suspend fun darkTheme(isDarkTheme: Boolean)
    suspend fun firstTime(isFirstTime: Boolean)
    suspend fun saveProfile(userProfileData: ProfileDataModel)
    suspend fun changeCurrency(currency: String)
    suspend fun clearData()

    object PreferenceKey {
        val PREF_IS_LOGIN = booleanPreferencesKey(PREFERENCE_IS_LOGIN)
        val PREF_IS_FIRST_TIME = booleanPreferencesKey(PREFERENCE_IS_FIRST_TIME)
        val PREF_NAME = stringPreferencesKey(PREFERENCE_NAME)
        val PREF_EMAIL = stringPreferencesKey(PREFERENCE_EMAIL)
        val PREF_PROFILE_URL = stringPreferencesKey(PREFERENCE_PROFILE_URL)
        val PREF_USER_ID = stringPreferencesKey(PREFERENCE_USER_ID)
        val PREF_LOCK_ENABLE = booleanPreferencesKey(PREFERENCE_LOCK_ENABLE)
        val PREF_DARK_THEME = booleanPreferencesKey(PREFERENCE_DARK_THEME)
        val PREF_CURRENCY = stringPreferencesKey(PREFERENCE_CURRENCY)
    }

    companion object {
        const val PREFERENCE_IS_LOGIN = "isLogin"
        const val PREFERENCE_IS_FIRST_TIME = "isFirstTime"
        const val PREFERENCE_NAME = "name"
        const val PREFERENCE_EMAIL = "email"
        const val PREFERENCE_PROFILE_URL = ""
        const val PREFERENCE_USER_ID = ""
        const val PREFERENCE_LOCK_ENABLE = "lock_enable"
        const val PREFERENCE_DARK_THEME = "dark_theme"
        const val PREFERENCE_CURRENCY = "currency"
    }
}
