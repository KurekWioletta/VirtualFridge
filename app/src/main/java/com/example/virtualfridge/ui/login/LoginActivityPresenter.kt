package com.example.virtualfridge.ui.login

import android.content.Intent
import com.example.virtualfridge.ui.login.google.GoogleLoginManager
import javax.inject.Inject

class LoginActivityPresenter @Inject constructor(
    private val view: LoginActivity,
    private val googleLoginManager: GoogleLoginManager
) {

    fun init() {
        googleLoginManager.initializeSignInClient()
    }

    fun checkForLoggedInUser() {
        // TODO: check for cached user
        googleLoginManager.checkForLoggedInUser()
    }

    fun loginClicked() {
        // TODO: call api
        view.openMainActivity()
    }

    fun loginGoogleClicked() = googleLoginManager.requestLogin()

    fun handleGoogleLoginResult(intent: Intent?) = googleLoginManager.login(intent)
}