package com.example.virtualfridge.ui.login.google

import android.content.Intent
import android.util.Log
import com.example.virtualfridge.ui.base.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject


class GoogleLoginManager @Inject constructor(
    private val activity: BaseActivity,
    private val googleLoginListener: GoogleLoginListener
) {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    fun initializeSignInClient() {
        mGoogleSignInClient = GoogleSignIn.getClient(
            activity,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestServerAuthCode(serverClientId)
                .requestEmail()
                .build()
        )
        logout()
    }

    fun checkForLoggedInUser() {
        val lastSignedInUser = GoogleSignIn.getLastSignedInAccount(activity)
        if (lastSignedInUser != null) {
            googleLoginListener.openMainActivity()
        }
    }

    fun requestLogin() = googleLoginListener.openGoogleLoginRequest(mGoogleSignInClient.signInIntent)

    fun login(intent: Intent?) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException::class.java)
            if (account != null) {
                // TODO: call service to save user to database
                // TODO: on success = save user data
                // TODO: on fail = logout user from google
                googleLoginListener.openMainActivity()
            }
        } catch (e: ApiException) {
            // TODO: Debug logs with Timber
            Log.w("LOGIN_ERROR", "signInResult:failed code=" + e.statusCode)
        }
    }

    fun logout() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(activity) {
                // TODO("Not yet implemented")
            }
    }

    fun deleteAccount() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(activity) {
                // TODO("Not yet implemented")
            }
    }
}