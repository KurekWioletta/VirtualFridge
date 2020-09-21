package com.example.virtualfridge.ui.login

import javax.inject.Inject

class LoginActivityPresenter @Inject constructor(
    private val view: LoginActivity
) {

    fun loginClicked() {
        // TODO: connect to server to login
        view.openMainActivity()
    }

    fun loginGoogleClicked() {
        // TODO: google login
        view.openMainActivity()
    }
}