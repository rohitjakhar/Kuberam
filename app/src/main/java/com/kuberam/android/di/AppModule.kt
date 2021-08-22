package com.kuberam.android.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kuberam.android.data.DataStorePreferenceStorage
import com.kuberam.android.data.DataStorePreferenceStorage.Companion.PREF__DATA_NAME
import com.kuberam.android.data.remote.RemoteDataSource
import com.kuberam.android.utils.Constant.USER_COLLECTION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val Context.datastore by preferencesDataStore(name = PREF__DATA_NAME)

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {

        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(PREF__DATA_NAME)
        }
    }

    @Singleton
    @Provides
    fun provideCollectionRef(): CollectionReference = Firebase.firestore.collection(USER_COLLECTION)

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        dataStorePreferenceStorage: DataStorePreferenceStorage,
        collectionReference: CollectionReference
    ): RemoteDataSource =
        RemoteDataSource(dataStorePreferenceStorage, collectionReference)
}
