package com.example.virtualfridge.data.internal

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.virtualfridge.data.internal.models.User
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataStore @Inject constructor(
    context: Context
) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "user_data")

    fun getUser() = dataStore.data
        .map { userData ->
            userData[preferencesKey<User>("logged_in_user")]
        }

    suspend fun cacheUser(user: User) = dataStore.edit { userData ->
        userData[preferencesKey<User>("logged_in_user")] = user
    }
}