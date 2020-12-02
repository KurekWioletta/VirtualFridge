package com.example.virtualfridge.data.internal

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.example.virtualfridge.data.internal.models.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Singleton
class UserDataStore constructor(
    context: Context
) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "user_data")

    fun user(): User? {
        var user: User?
        runBlocking {
            user =
                dataStore.data.map { userData ->
                    if (userData[preferencesKey<String>("email")].isNullOrEmpty()) {
                        null
                    } else {
                        User(
                            userData[preferencesKey<String>("id")] ?: "",
                            userData[preferencesKey<String>("email")] ?: "",
                            userData[preferencesKey<String>("first_name")] ?: "",
                            userData[preferencesKey<String>("last_name")] ?: "",
                            userData[preferencesKey<String>("family_name")],
                            userData[preferencesKey<Boolean>("account_confirmed")] ?: false
                        )
                    }
                }.first()
        }
        return user
    }

    fun loggedInUser(): User = user()!!

    fun cacheUser(user: User) = GlobalScope.launch {
        dataStore.edit { userData ->
            userData[preferencesKey<String>("id")] = user.id
            userData[preferencesKey<String>("email")] = user.email
            userData[preferencesKey<String>("first_name")] = user.firstName
            userData[preferencesKey<String>("last_name")] = user.lastName
            userData[preferencesKey<Boolean>("account_confirmed")] = user.accountConfirmed
            user.familyName?.run {
                userData[preferencesKey<String>("family_name")] = this
            }
        }
    }

    fun addToFamily(familyName: String) = runBlocking {
        dataStore.edit { userData ->
            userData[preferencesKey<String>("family_name")] = familyName
        }
    }

    fun removeFromFamily() =
        runBlocking {
            dataStore.edit { userData ->
                userData.remove(preferencesKey<String>("family_name"))
            }
        }

    fun familyName(): String? {
        var familyName: String?
        runBlocking {
            familyName =
                dataStore.data.map { userData ->
                    userData[preferencesKey<String>("family_name")]
                }.first()
        }
        return familyName
    }

    fun clearStore() = GlobalScope.launch {
        dataStore.edit { userData ->
            userData.clear()
        }
    }
}