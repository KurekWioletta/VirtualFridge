package com.example.virtualfridge.ui.login.google

import android.content.Intent
import com.example.virtualfridge.ui.login.LoginListener

interface GoogleLoginListener: LoginListener {
    companion object {
        const val RC_GOOGLE_LOGIN_REQUEST = 1000
    }

    fun openGoogleLoginRequest(intent: Intent)
}