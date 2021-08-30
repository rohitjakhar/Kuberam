package com.kuberam.android.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.kuberam.android.data.model.ProfileDataModel
import com.kuberam.android.utils.PreferenceDataStore
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_CURRENCY
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_DARK_THEME
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_EMAIL
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_IS_FIRST_TIME
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_IS_LOGIN
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_LOCK_ENABLE
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_NAME
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_PROFILE_URL
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_USER_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStorePreferenceStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceDataStore {
    companion object {
        const val PREF__DATA_NAME = "kuberam"
    }

    override val isLogin: Flow<Boolean>
        get() = dataStore.data.map {
            it[PREF_IS_LOGIN] ?: false
        }

    override val currenetCurrency: Flow<String>
        get() = dataStore.data.map {
            it[PREF_CURRENCY] ?: "INR"
        }
    override val isLockEnable: Flow<Boolean>
        get() = dataStore.data.map {
            it[PREF_LOCK_ENABLE] ?: false
        }

    override val isFirstTime: Flow<Boolean>
        get() = dataStore.data.map {
            it[PREF_IS_FIRST_TIME] ?: true
        }

    override val isDarkTheme: Flow<Boolean>
        get() = dataStore.data.map {
            it[PREF_DARK_THEME] ?: true
        }

    override val userProfileData: Flow<ProfileDataModel>
        get() = dataStore.data.map {
            ProfileDataModel(
                name = it[PREF_NAME] ?: "",
                email = it[PREF_EMAIL] ?: "",
                profileUrl = it[PREF_PROFILE_URL] ?: "",
                userId = it[PREF_USER_ID] ?: ""
            )
        }

    override suspend fun changeCurrency(curreny: String) {
        dataStore.edit {
            it[PREF_CURRENCY] = curreny
        }
    }

    override suspend fun isLogin(isLogin: Boolean) {
        dataStore.edit {
            it[PREF_IS_LOGIN] = isLogin
        }
    }

    override suspend fun isLockEnable(isLockEnable: Boolean) {
        dataStore.edit {
            it[PREF_LOCK_ENABLE] = isLockEnable
        }
    }

    override suspend fun darkTheme(isDarkTheme: Boolean) {
        dataStore.edit {
            it[PREF_DARK_THEME] = isDarkTheme
        }
    }

    override suspend fun firstTime(isFirstTime: Boolean) {
        dataStore.edit {
            it[PREF_IS_FIRST_TIME] = isFirstTime
        }
    }

    override suspend fun saveProfile(userProfileData: ProfileDataModel) {
        dataStore.edit {
            it[PREF_NAME] = userProfileData.name
            it[PREF_EMAIL] = userProfileData.email
            it[PREF_PROFILE_URL] = userProfileData.profileUrl
            it[PREF_USER_ID] = userProfileData.userId
        }
    }

    override suspend fun clearData() {
        dataStore.edit {
            it.clear()
        }
    }
}
