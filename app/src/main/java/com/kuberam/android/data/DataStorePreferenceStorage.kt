package com.kuberam.android.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.kuberam.android.data.model.ProfileModel
import com.kuberam.android.utils.PreferenceDataStore
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_EMAIL
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_IS_FIRST_TIME
import com.kuberam.android.utils.PreferenceDataStore.PreferenceKey.PREF_IS_LOGIN
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
    override val isFirstTime: Flow<Boolean>
        get() = dataStore.data.map {
            it[PREF_IS_FIRST_TIME] ?: false
        }

    override val userProfile: Flow<ProfileModel>
        get() = dataStore.data.map {
            ProfileModel(
                name = it[PREF_NAME] ?: "",
                email = it[PREF_EMAIL] ?: "",
                profileUrl = it[PREF_PROFILE_URL] ?: "",
                userId = it[PREF_USER_ID] ?: ""
            )
        }

    override suspend fun isLogin(isLogin: Boolean) {
        dataStore.edit {
            it[PREF_IS_LOGIN] = isLogin
        }
    }

    override suspend fun firstTime(isFirstTime: Boolean) {
        dataStore.edit {
            it[PREF_IS_FIRST_TIME] = isFirstTime
        }
    }

    override suspend fun saveProfile(userProfile: ProfileModel) {
        dataStore.edit {
            it[PREF_NAME] = userProfile.name
            it[PREF_EMAIL] = userProfile.email
            it[PREF_PROFILE_URL] = userProfile.profileUrl
            it[PREF_USER_ID] = userProfile.userId
        }
    }

    override suspend fun clearData() {
        dataStore.edit {
            it.clear()
        }
    }
}
