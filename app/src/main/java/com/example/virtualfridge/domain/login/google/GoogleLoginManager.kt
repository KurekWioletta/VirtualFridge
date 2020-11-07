package com.example.virtualfridge.domain.login.google

import android.content.Intent
import com.example.virtualfridge.data.api.ExampleApi
import com.example.virtualfridge.data.api.models.mapToUser
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.base.BaseActivity
import com.example.virtualfridge.domain.login.LogoutManager
import com.example.virtualfridge.utils.RxTransformerManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject

class GoogleLoginManager @Inject constructor(
    private val activity: BaseActivity,
    private val exampleApi: ExampleApi,
    private val loginManager: LogoutManager,
    private val userDataStore: UserDataStore,
    private val googleLoginListener: GoogleLoginListener,
    private val rxTransformerManager: RxTransformerManager
) {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    fun initializeSignInClient() {
        if (!this::mGoogleSignInClient.isInitialized) {
            mGoogleSignInClient = GoogleSignIn.getClient(
                activity,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    // TODO: think of authorization and authentication
                    //.requestServerAuthCode(serverClientId)
                    .requestEmail()
                    .build()
            )
        }
        logout()
    }

    fun userLoggedIn() = GoogleSignIn.getLastSignedInAccount(activity) != null

    fun requestLogin() =
        googleLoginListener.openGoogleLoginRequest(mGoogleSignInClient.signInIntent)

    fun login(intent: Intent?) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(intent)
                .getResult(ApiException::class.java)
            if (account != null) {
                activity.registerViewSubscription(exampleApi
                    .registerUserWithGoogle(
                        account.email ?: "",
                        account.id ?: "",
                        account.givenName ?: "",
                        account.familyName ?: ""
                    )
                    .doOnNext { userDataStore.cacheUser(it.mapToUser()) }
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .doOnSubscribe { activity.showLoading() }
                    .doOnTerminate { activity.hideLoading() }
                    .doOnError { logout() }
                    .subscribe({
                        // TODO: in response get info if user confirmed
                        //googleLoginListener.openMainActivity()
                        //startActivity(intent,MainActivity::class.java)

                    }, {
                        // TODO: generic error handling
                    })
                )
            }
        } catch (e: ApiException) {
            googleLoginListener.showGoogleLoginError()
        }
    }

    fun logout() = mGoogleSignInClient.signOut()
        .addOnCompleteListener(activity) {
            loginManager.logout()
        }

    fun deleteAccount() = mGoogleSignInClient.revokeAccess()
        .addOnCompleteListener(activity) {
            // TODO("Not yet implemented")
        }

}