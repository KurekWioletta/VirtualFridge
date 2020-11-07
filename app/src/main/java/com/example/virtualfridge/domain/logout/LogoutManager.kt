package com.example.virtualfridge.domain.logout

import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.login.google.GoogleLoginManager
import javax.inject.Inject

class LogoutManager @Inject constructor(
    private val googleLoginManager: GoogleLoginManager,
    private val userDataStore: UserDataStore
) {

    fun logout() {
        googleLoginManager.initializeSignInClient()
        if (googleLoginManager.userLoggedIn()) {
            googleLoginManager.logout()
        }
        userDataStore.clearStore()
    }

}