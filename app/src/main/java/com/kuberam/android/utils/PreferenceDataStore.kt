package com.kuberam.android.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kuberam.android.data.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface PreferenceDataStore {
    val isLogin: Flow<Boolean>
    val isFirstTime: Flow<Boolean>
    val userProfile: Flow<ProfileModel>

    suspend fun isLogin(isLogin: Boolean)
    suspend fun firstTime(isFirstTime: Boolean)
    suspend fun saveProfile(userProfile: ProfileModel)
    suspend fun clearData()

    object PreferenceKey {
        val PREF_IS_LOGIN = booleanPreferencesKey(PREFERENCE_IS_LOGIN)
        val PREF_IS_FIRST_TIME = booleanPreferencesKey(PREFERENCE_IS_FIRST_TIME)
        val PREF_NAME = stringPreferencesKey(PREFERENCE_NAME)
        val PREF_EMAIL = stringPreferencesKey(PREFERENCE_EMAIL)
        val PREF_PROFILE_URL = stringPreferencesKey(PREFERENCE_PROFILE_URL)
        val PREF_USER_ID = stringPreferencesKey(PREFERENCE_USER_ID)
    }

    companion object {
        const val PREFERENCE_IS_LOGIN = "isLogin"
        const val PREFERENCE_IS_FIRST_TIME = "isFirstTime"
        const val PREFERENCE_NAME = "name"
        const val PREFERENCE_EMAIL = "email"
        const val PREFERENCE_PROFILE_URL = ""
        const val PREFERENCE_USER_ID = ""
    }
}
