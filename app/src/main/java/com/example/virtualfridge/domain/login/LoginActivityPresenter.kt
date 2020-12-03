package com.example.virtualfridge.domain.login

import android.content.Intent
import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.UserApi
import com.example.virtualfridge.data.api.models.mapToUser
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.login.google.GoogleLoginManager
import com.example.virtualfridge.utils.ApiErrorParser
import com.example.virtualfridge.utils.RxTransformerManager
import com.example.virtualfridge.utils.validate
import com.example.virtualfridge.utils.validationResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class LoginActivityPresenter @Inject constructor(
    private val view: LoginActivity,
    private val userApi: UserApi,
    private val userDataStore: UserDataStore,
    private val rxTransformerManager: RxTransformerManager,
    private val apiErrorParser: ApiErrorParser,
    private val googleLoginManager: GoogleLoginManager
) {

    fun init() = googleLoginManager.initializeSignInClient()

    fun resume() = googleLoginManager.initializeSignInClient()

    fun checkForLoggedInUser() {
        if (userDataStore.user() != null) {
            view.openMainActivity()
        } else {
            if (googleLoginManager.userLoggedIn()) {
                googleLoginManager.logout()
            }
        }
    }

    fun loginClicked(email: String, password: String) {
        val validationViewModel = LoginActivity.ValidationViewModel(
            email.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() },
            password.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() }
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    // TODO: error tracking
                    view.showAlert(view.getString(R.string.login_login_error))
                    return@OnCompleteListener
                }

                view.registerViewSubscription(userApi.loginUser(email, password)
                    .doOnNext { userDataStore.cacheUser(it.mapToUser()) }
                    .flatMap {
                        userApi.notifications(
                            userId = it.id,
                            messagingToken = task.result
                        )
                    }
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .doOnSubscribe { view.showLoading() }
                    .doOnTerminate { view.hideLoading() }
                    .subscribe({
                        view.openMainActivity()
                    }, {
                        view.showAlert(apiErrorParser.parse(it))
                    })
                )
            }).addOnFailureListener {
                view.showAlert(view.getString(R.string.login_login_error))
            }
        }
    }

    fun loginGoogleClicked() = googleLoginManager.requestLogin()

    fun handleGoogleLoginResult(intent: Intent?) = googleLoginManager.login(intent)
}